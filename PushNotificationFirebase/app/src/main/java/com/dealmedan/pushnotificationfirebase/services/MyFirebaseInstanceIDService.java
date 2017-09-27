package com.dealmedan.pushnotificationfirebase.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dealmedan.pushnotificationfirebase.config.Config;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by daniel on 4/28/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        
        //saveing reg id to shared preferences
        storeRegIdInPref(refreshedToken);
        //sending reg id to your server
        sendRegistrationToServer(refreshedToken);
        Intent registrationComplate = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplate.putExtra("token",refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplate);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.e("Rigistration To Server", "sendRegistrationToServer" + refreshedToken);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF,0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId",token);
        editor.commit();
    }
}
