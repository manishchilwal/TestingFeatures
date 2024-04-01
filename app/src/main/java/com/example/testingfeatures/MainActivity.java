package com.example.testingfeatures;
//
//import static com.clevertap.android.sdk.CleverTapAPI.createNotificationChannel;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.NotificationManager;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.location.Location;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
//import com.clevertap.android.sdk.CleverTapAPI;
//import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
//import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
//import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
//import com.clevertap.android.sdk.interfaces.NotificationHandler;
//
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity implements DisplayUnitListener {
//    CleverTapAPI clevertapDefaultInstance;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
//        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);
//
//        Location location = clevertapDefaultInstance.getLocation();
//        clevertapDefaultInstance.setLocation(location);
//        clevertapDefaultInstance.enableDeviceNetworkInfoReporting(true);
//
//
//        // Check if notifications are enabled, if not prompt user to enable
//        if (!isNotificationEnabled()) {
//            showNotificationEnableDialog();
//        }
//
//
//        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
//        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
//
//        Button signInButton = findViewById(R.id.sign_in_button);
//        Button loginButton = findViewById(R.id.login_button);
//
//        signInButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, SignInActivity.class));
//            }
//        });
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });
//
//        createNotificationChannel(getApplicationContext(),"manishTest","Your Channel Name","Your Channel Description", NotificationManager.IMPORTANCE_MAX,true);
//    }
//    @Override
//    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
//        // you will get display units here
//        for (int i = 0; i < units.size(); i++) {
//            CleverTapDisplayUnit unit = units.get(i);
//            Log.v("Native", String.valueOf(unit));
//            prepareDisplayView(unit);
//        }
//    }
//
//    private void prepareDisplayView(CleverTapDisplayUnit unit) {
//
//        LinearLayout parentLayout = findViewById(R.id.linear);
//        for (CleverTapDisplayUnitContent contentItem : unit.getContents()) {
//            String media = contentItem.getMedia();
//            System.out.println("Media: " + media);
//            ImageView imageView = new ImageView(this);
//            Glide.with(this).load(media).into(imageView);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT
//            );
//            imageView.setLayoutParams(layoutParams);
//            parentLayout.addView(imageView);
//        }
//    }
//    private boolean isNotificationEnabled() {
//        NotificationManager notificationManager = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            return notificationManager.getNotificationChannel("manishTest").getImportance() != NotificationManager.IMPORTANCE_NONE;
//        }
//        return false;
//    }
//
//    private void showNotificationEnableDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Notifications are disabled for this app. Do you want to enable them?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // Redirect user to app notification settings
//                        Toast.makeText(MainActivity.this, "Redirect user to notification settings", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User chose not to enable notifications, handle accordingly
//                        Toast.makeText(MainActivity.this, "Notifications disabled", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        AlertDialog alert = builder.create();
//        alert.show();
//    }
//}


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
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
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
import com.clevertap.android.sdk.interfaces.NotificationHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DisplayUnitListener {
    private static final String CHANNEL_ID = "manishTest";
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

        // Check if notifications are enabled, if not prompt user to enable
        if (!isNotificationEnabled()) {
            showNotificationEnableDialog();
        }

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

    private boolean isNotificationEnabled() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return notificationManager.getNotificationChannel(CHANNEL_ID).getImportance() != NotificationManager.IMPORTANCE_NONE;
        }
        return false;
    }

    private void showNotificationEnableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Notifications are disabled for this app. Do you want to enable them?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    // Redirect user to app notification settings
                    Toast.makeText(MainActivity.this, "Redirect user to notification settings", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    // User chose not to enable notifications, handle accordingly
                    Toast.makeText(MainActivity.this, "Notifications disabled", Toast.LENGTH_SHORT).show();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
