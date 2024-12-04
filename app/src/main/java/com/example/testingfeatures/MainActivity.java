package com.example.testingfeatures;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.InAppNotificationButtonListener;
import com.clevertap.android.sdk.PushPermissionResponseListener;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
import com.clevertap.android.sdk.inapp.CTInAppNotification;
import com.clevertap.android.sdk.inapp.CTLocalInApp;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.clevertap.android.sdk.variables.callbacks.FetchVariablesCallback;
import com.google.firebase.FirebaseApp;

import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements DisplayUnitListener, PushPermissionResponseListener, InAppNotificationButtonListener {
    private static final String CHANNEL_ID = "manishTest";

    private static final String TAG = "MainActivity";
    private CleverTapAPI clevertapDefaultInstance;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        //clevertapDefaultInstance.pushFcmRegistrationId("abc",false);
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);

        clevertapDefaultInstance.setInAppNotificationButtonListener(this);

        //clevertapDefaultInstance.setOptOut(false);
        Location location = clevertapDefaultInstance.getLocation();
        clevertapDefaultInstance.setLocation(location);
        clevertapDefaultInstance.enableDeviceNetworkInfoReporting(true);

        clevertapDefaultInstance.fetchVariables(new FetchVariablesCallback() {
            @Override
            public void onVariablesFetched(boolean isSuccess) {
                // isSuccess is true when server request is successful, false otherwise
            }
        });

//        Locale locale = getLocale("en", "US");
//        System.out.println("Locale: " + locale.toString());

        String locale = clevertapDefaultInstance.getLocale();

        clevertapDefaultInstance.setLocale("en_IN");


        if (clevertapDefaultInstance != null) {
            clevertapDefaultInstance.registerPushPermissionNotificationResponseListener(this);
        }

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

    public static Locale getLocale(String language, String country) {
        return new Locale(language, country);
    }


    @Override
    public void onInAppButtonClick(HashMap<String, String> hashMap) {
        if (hashMap != null) {
            // Retrieve the custom URL from the hashmap
            String customUrl = hashMap.get("customUrl");

            if (customUrl != null && !customUrl.isEmpty()) {
                try {
                    // Create an intent with the deep link URL
                    Uri deepLinkUri = Uri.parse(customUrl);
                    Intent intent = new Intent(Intent.ACTION_VIEW, deepLinkUri);

                    // Check if there's an activity that can handle this intent to prevent crashes
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        // Log if no activity can handle the deep link
                        Log.d("InAppButtonClickData", "No activity found to handle the deep link URL: " + customUrl);
                    }
                } catch (Exception e) {
                    // Catch potential exceptions for invalid URLs
                    Log.e("InAppButtonClickData", "Error processing the deep link: " + e.getMessage());
                }
            } else {
                // Log if the customUrl is missing or empty
                Log.d("InAppButtonClickData", "customUrl is missing or empty.");
            }
        } else {
            // Log if the hashmap is null
            Log.d("InAppButtonClickData", "No data received.");
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        // Call promptPushPrimer from onResume
         jsonObject = CTLocalInApp.builder()
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


        if(!clevertapDefaultInstance.isPushPermissionGranted()){
            clevertapDefaultInstance.promptPushPrimer(jsonObject);
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        /**
         * On Android 12, Raise notification clicked event when Activity is already running in activity backstack
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            clevertapDefaultInstance.pushNotificationClickedEvent(intent.getExtras());
            Log.d("NotificationClick", "Notification notification clicked in foreground or background state: ");
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (clevertapDefaultInstance != null) {
            clevertapDefaultInstance.unregisterPushPermissionNotificationResponseListener(this);
        }
    }
}
