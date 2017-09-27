package com.dealmedan.pushnotificationfirebase.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.dealmedan.pushnotificationfirebase.activity.MainActivity;
import com.dealmedan.pushnotificationfirebase.config.Config;
import com.dealmedan.pushnotificationfirebase.util.NotificationUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by daniel on 4/28/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private NotificationUtil notificationUtil;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null) {
            return;
        }
        //check ig message contain a notification payload
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Notification Body : " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }
        //check if message contains a data payload
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleDataMessage(JSONObject json) {
        try {
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timeStamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");


            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timeStamp);

            if (!NotificationUtil.isAppIsInBackground(getApplicationContext())){
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message",message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                NotificationUtil notificationUtils = new NotificationUtil(getApplicationContext());
                notificationUtils.playNotificationSound();
            }else {
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                resultIntent.putExtra("message", message);

                if (TextUtils.isEmpty(imageUrl)){
                    showNotificationMessage(getApplicationContext(), title, message, timeStamp, resultIntent);
                }else {
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timeStamp, resultIntent, imageUrl);
                }
            }

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e){
            Log.e(TAG, "Exception: " + e.getMessage());
            e.getMessage();
        }
    }

    private void showNotificationMessageWithBigImage(Context applicationContext, String title, String message, String timeStamp, Intent resultIntent, String imageUrl) {
        notificationUtil = new NotificationUtil(applicationContext);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtil.showNotificationMessage(title,message,timeStamp,resultIntent,imageUrl);
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent resultIntent) {
        notificationUtil = new NotificationUtil(context);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtil.showNotificationMessage(title, message, timeStamp, resultIntent);
    }

    private void handleNotification(String message) {
        if (!NotificationUtil.isAppIsInBackground(getApplicationContext())){
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message",message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            //playnotification sound
            NotificationUtil notificationUtils = new NotificationUtil(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else {
            // If the app is in background, firebase itself handles the notification
        }
    }
}
