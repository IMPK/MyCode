package com.onmobile.onesignalsample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationPayload;

import org.json.JSONException;
import org.json.JSONObject;

public class NotificationExtenderCustomNotificationService extends NotificationExtenderService {

    public NotificationExtenderCustomNotificationService() {

    }

    @Override
    protected boolean onNotificationProcessing(OSNotificationPayload notification) {
        Log.e("onNotificationProcessing","called");

        if(notification.backgroundData){
            JSONObject json = notification.additionalData;
            try {
                if(json != null){
                    String str = json.getString("pk");
                    Log.e("additional data :- ", str );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OverrideSettings overrideSettings = new OverrideSettings();
            overrideSettings.extender = new NotificationCompat.Extender() {
             // Apply this extender to a notification builder.

             //@param builder the builder to be modified.
             //@return the build object for chaining.

            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                builder.setContentTitle("PK");
                builder.setContentText("text");
                builder.setSmallIcon(R.mipmap.apps_notifications_icon);


                return builder;

                }
            };
            OSNotificationDisplayedResult result = displayNotification(overrideSettings);
            return true;
        }else{
            return false;
        }

 /*       String title = notification.title;
        String message = notification.message;
        JSONObject jsonObject = notification.additionalData;
        if(jsonObject != null){
            String jsonString = jsonObject.toString();
            String jsonStr = jsonString;
        }

        sendNotification(title,message);*/
    }

    private void sendNotification(String title,String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setSmallIcon(R.mipmap.apps_notifications_icon)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if(title != null && !title.equalsIgnoreCase("") && !title.isEmpty()){
            notificationBuilder.setContentTitle("pk");
        }else{
            notificationBuilder.setContentTitle("Title (use console to change)");
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify((int)System.currentTimeMillis(), notificationBuilder.build());
    }


}
