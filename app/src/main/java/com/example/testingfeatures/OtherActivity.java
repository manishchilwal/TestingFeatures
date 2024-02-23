package com.example.testingfeatures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;

public class OtherActivity extends AppCompatActivity {

    Button basicNotificationButton,autoCarouselButton,manualCarouselButton,flimstripVariantButton,ratingTemplateButton,productCatalogButton,productCatalogLinearViewButton,timerButton,zerobezelButton,inputBoxCTAButton,inputBoxRemindLater,inputBoxReplyAsAnEventButton,inputBoxReplyAsAnIntentButton;
    String username;
    CleverTapAPI clevertapDefaultInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        username = getIntent().getStringExtra("username");

        basicNotificationButton = findViewById(R.id.basic_notification_button);
        autoCarouselButton = findViewById(R.id.auto_carousel_button);
        manualCarouselButton = findViewById(R.id.manual_carousel_button);
        flimstripVariantButton = findViewById(R.id.filmstrip_varient_button);
        ratingTemplateButton = findViewById(R.id.rating_button);
        productCatalogButton = findViewById(R.id.product_catalog_button);
        productCatalogLinearViewButton = findViewById(R.id.product_catalog_linear_view_button);
        timerButton = findViewById(R.id.timer_button);
        zerobezelButton = findViewById(R.id.zero_bezel_button);
        inputBoxCTAButton = findViewById(R.id.input_box_cta_button);
        inputBoxRemindLater = findViewById(R.id.input_box_cta_remind_later_button);
        inputBoxReplyAsAnEventButton = findViewById(R.id.input_box_reply_as_an_event_button);
        inputBoxReplyAsAnIntentButton = findViewById(R.id.input_box_reply_as_an_intent_button);

        basicNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Basic Notification");
            }
        });

        autoCarouselButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Auto Carousel Notification");
            }
        });

        manualCarouselButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Manual Carousel Notification");
            }
        });

        flimstripVariantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Flimstrip Variant Notification");
            }
        });

        ratingTemplateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Rating Template Notification");
            }
        });

        productCatalogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Product Catalog Notification");
            }
        });

        productCatalogLinearViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Product Catalog Linear View Notification");
            }
        });

        timerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Timer Notification");
            }
        });

        zerobezelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Zero Bezel Notification");
            }
        });

        inputBoxCTAButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Input Box CTA Notification");
            }
        });

        inputBoxRemindLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Input Box Remind Later Notification");
            }
        });

        inputBoxReplyAsAnEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Input Box Replay As An Event Notification");
            }
        });

        inputBoxReplyAsAnIntentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("Input Box Reply As An Intent Notification");
            }
        });

    }
}
