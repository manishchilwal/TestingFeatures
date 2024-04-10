package com.example.testingfeatures;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.PushPermissionResponseListener;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
import com.clevertap.android.sdk.inapp.CTLocalInApp;
import com.clevertap.android.sdk.interfaces.NotificationHandler;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DisplayUnitListener, PushPermissionResponseListener  {
    private static final String CHANNEL_ID = "manishTest";

    private static final String TAG = "MainActivity";
    private CleverTapAPI clevertapDefaultInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);

        Location location = clevertapDefaultInstance.getLocation();
        clevertapDefaultInstance.setLocation(location);
        clevertapDefaultInstance.enableDeviceNetworkInfoReporting(true);


        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());

        Button signInButton = findViewById(R.id.sign_in_button);
        Button loginButton = findViewById(R.id.login_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        // Create notification channel if not already created
        CleverTapAPI.createNotificationChannel(getApplicationContext(), CHANNEL_ID, "Your Channel Name", "Your Channel Description", NotificationManager.IMPORTANCE_MAX, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Call promptPushPrimer from onResume
        JSONObject jsonObject = CTLocalInApp.builder()
                .setInAppType(CTLocalInApp.InAppType.HALF_INTERSTITIAL)
                .setTitleText("Get Notified")
                .setMessageText("Please enable notifications on your device to use Push Notifications.")
                .followDeviceOrientation(true)
                .setPositiveBtnText("Allow")
                .setNegativeBtnText("Cancel")
                .setBackgroundColor("#" + Integer.toHexString(com.example.testingfeatures.Constants.WHITE))
                .setBtnBorderColor("#" + Integer.toHexString(com.example.testingfeatures.Constants.BLUE))
                .setTitleTextColor("#" + Integer.toHexString(com.example.testingfeatures.Constants.BLUE))
                .setMessageTextColor("#" + Integer.toHexString(com.example.testingfeatures.Constants.BLACK))
                .setBtnTextColor("#" + Integer.toHexString(com.example.testingfeatures.Constants.WHITE))
                .setImageUrl("https://i.ibb.co/BtfMqsm/inbox.png")
                .setBtnBackgroundColor("#" + Integer.toHexString(Constants.BLUE))
                .build();

        // Invoke the Push Primer flow with the created JSON object
        clevertapDefaultInstance.promptPushPrimer(jsonObject);
    }

    @Override
    public void onPushPermissionResponse(boolean accepted) {
        Log.i(TAG, "onPushPermissionResponse :  InApp---> response() called accepted=" + accepted);
        if (accepted) {
            CleverTapAPI.createNotificationChannel(getApplicationContext(), "manishTest", "Testing Channel",
                    "Testing Channel for BR", NotificationManager.IMPORTANCE_HIGH, true);
        }
    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        LinearLayout parentLayout = findViewById(R.id.linear);
        for (CleverTapDisplayUnit unit : units) {
            for (CleverTapDisplayUnitContent contentItem : unit.getContents()) {
                String media = contentItem.getMedia();
                Log.d("Media", "Media: " + media);
                ImageView imageView = new ImageView(this);
                Glide.with(this).load(media).into(imageView);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );
                imageView.setLayoutParams(layoutParams);
                parentLayout.addView(imageView);
            }
        }
    }
}
