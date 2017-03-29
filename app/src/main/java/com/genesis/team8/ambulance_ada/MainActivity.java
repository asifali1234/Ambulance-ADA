package com.genesis.team8.ambulance_ada;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ASIF ALI on 13/01/17.
 */

public class MainActivity extends ActionBarActivity {
    ArrayList<String> lat = new ArrayList<String>();
    ArrayList<String> lng = new ArrayList<String>();
    String  p;
    String f;
    String fl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        read();
scheduleAlarm();
    }

    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                1000, pIntent);

    }
    public void read()
    {
        final Firebase ref = new Firebase("https://ad-a-bc752.firebaseio.com/PreviousLocation/");
        //Value event listener for realtime data update


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot usersSnapshot) {

                for (DataSnapshot userSnapshot : usersSnapshot.getChildren()) {
                    location ord = userSnapshot.getValue(location.class);
                    p = ord.getLat();

                    f = ord.getLng();

                    fl=ord.getYes();
                }
                cal();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }
    public  void cal()
    {
        if(fl.equals("t"))
        {
        Intent in = new Intent(this, MapsActivity.class);
        Toast.makeText(this,p+f, Toast.LENGTH_LONG ).show();
        in.putExtra("lat", p);
        in.putExtra("lng", f);
        startActivity(in);
    }else
        {
            Intent in = new Intent(this, noreq.class);

            startActivity(in);
        }}

}
