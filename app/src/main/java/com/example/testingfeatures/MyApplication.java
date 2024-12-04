package com.example.testingfeatures;

import android.app.Application;
import android.util.Log;

import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener;
import com.google.firebase.FirebaseApp;

import java.util.HashMap;

public class MyApplication extends Application implements CTPushNotificationListener {
    @Override
    public void onCreate(){
        ActivityLifecycleCallback.register(this);
        super.onCreate();
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
        CleverTapAPI cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        cleverTapAPI.setCTPushNotificationListener(this);
    }

    @Override
    public void onNotificationClickedPayloadReceived(HashMap<String, Object> payload) {
        Log.d("NotificationClick", "Notification notification clicked in killed state: " + payload.toString());
    }
}