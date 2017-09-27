package com.dealmedan.pushnotificationfirebase.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.dealmedan.pushnotificationfirebase.R;
import com.dealmedan.pushnotificationfirebase.config.Config;
import com.dealmedan.pushnotificationfirebase.util.NotificationUtil;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    private static final  String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)){
                    //gcm successfully registered
                    //now subscribe to 'goal' topic to receive app wide notification
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                }else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)){
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(),"Push Notification: " + message, Toast.LENGTH_LONG).show();

                    txtMessage.setText(message);

                }
            }
        };
        displayFirebaseRegId();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e("Firebase Reg Id", " " + regId);

        if (!TextUtils.isEmpty(regId)){
            txtRegId.setText("Firebase ID" + regId);
        }else {
            txtRegId.setText("Firebase Reg ID is not Receive yet!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtil.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
}
