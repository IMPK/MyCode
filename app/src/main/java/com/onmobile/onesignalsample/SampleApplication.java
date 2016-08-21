package com.onmobile.onesignalsample;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by prateek.khurana on 8/1/2016.
 */
public class SampleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.DEBUG);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.ERROR, OneSignal.LOG_LEVEL.ERROR);
        OneSignal.startInit(this).setNotificationOpenedHandler(new ExampleNotificationOpenedHandler()).init();
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                Log.e("UserId : " , userId);
                Log.e("registrationId : " , registrationId);
            }
        });
    }

    private class ExampleNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
            try {
                if (additionalData != null) {
                    if (additionalData.has("song_id")){
                        Log.d("OneSignalExample", "OneSignal notification with song id " + additionalData.getInt("song_id") + " pressed");
                    }


                    Log.d("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }

            // The following can be used to open an Activity of your choice.
      Intent intent = new Intent(getApplicationContext(), NotificationActionActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
            // Follow the instructions in the link below to prevent the launcher Activity from starting.
            // https://documentation.onesignal.com/docs/android-notification-customizations#changing-the-open-action-of-a-notification
        }
    }
}
