package com.example.testingfeatures;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
import com.clevertap.android.sdk.interfaces.NotificationHandler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DisplayUnitListener {
    CleverTapAPI clevertapDefaultInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.getDefaultInstance(this).setDisplayUnitListener(this);

        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);

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

        CleverTapAPI.createNotificationChannel(getApplicationContext(),"manishTest","Your Channel Name","Your Channel Description", NotificationManager.IMPORTANCE_MAX,true);
    }
    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        // you will get display units here
        for (int i = 0; i < units.size(); i++) {
            CleverTapDisplayUnit unit = units.get(i);
            Log.v("Native", String.valueOf(unit));
            prepareDisplayView(unit);
        }
    }

    private void prepareDisplayView(CleverTapDisplayUnit unit) {

        LinearLayout parentLayout = findViewById(R.id.linear);
        for (CleverTapDisplayUnitContent contentItem : unit.getContents()) {
            String media = contentItem.getMedia();
            System.out.println("Media: " + media);
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
