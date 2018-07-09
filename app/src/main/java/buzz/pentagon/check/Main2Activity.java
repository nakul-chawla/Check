package buzz.pentagon.check;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;


public class Main2Activity extends ListActivity {
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(Main2Activity.this,MyService.class);

        startService(intent);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
        // Set smsList in the ListAdapter


    }
    public ServiceConnection mConnection =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           MyService.LocalBinder binder=(MyService.LocalBinder)service;
           binder.getServiceInstance().registerActivity(Main2Activity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        SMSData sms = (SMSData)getListAdapter().getItem(position);
//
//        Toast.makeText(getApplicationContext(), sms.getBody(), Toast.LENGTH_LONG).show();
//
//    }
//
}