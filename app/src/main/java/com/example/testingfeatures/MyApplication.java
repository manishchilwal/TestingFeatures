package com.example.testingfeatures;

import android.app.Application;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.google.firebase.FirebaseApp;

public class MyApplication extends Application {
    @Override
    public void onCreate(){
        ActivityLifecycleCallback.register(this);
        super.onCreate();
        FirebaseApp.initializeApp(this);

    }
}