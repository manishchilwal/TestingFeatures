package com.example.testingfeatures;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;
//import com.clevertap.android.sdk.interfaces.OnInitCleverTapIDListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MainPageActivity extends AppCompatActivity implements CTInboxListener, DisplayUnitListener {

    Button requestInbox,getAllInboxMessage,updateProfileButton,addToCartButton,productViewButton,chargeButton,appInboxButton,otherButton,setIdentityButton;
    String username,couponNumber;
    CleverTapAPI clevertapDefaultInstance;
//
//    //fcm realtime database
//    private DatabaseReference numbersRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        clevertapDefaultInstance.setCTNotificationInboxListener(this);
        clevertapDefaultInstance.setDisplayUnitListener(this);
        clevertapDefaultInstance.initializeInbox();
        username = getIntent().getStringExtra("username");

        TextView welcomeMessageTextView = findViewById(R.id.welcome_message_text);
        welcomeMessageTextView.setText("Welcome to the Main Page, " + username + "!");

        updateProfileButton = findViewById(R.id.update_profile_button);
        addToCartButton = findViewById(R.id.add_to_cart_button);
        productViewButton = findViewById(R.id.product_view_button);
        chargeButton = findViewById(R.id.charge_button);
        appInboxButton = findViewById(R.id.app_inbox_button);
        otherButton = findViewById(R.id.other_button);
        requestInbox = findViewById(R.id.request_inbox);
        getAllInboxMessage = findViewById(R.id.getallinboxmessage);
        setIdentityButton = findViewById(R.id.set_identity_button);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        numbersRef = database.getReference("number");

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, UpdateProfileActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        getAllInboxMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch the result of getAllInboxMessages
                Object inboxMessages = clevertapDefaultInstance.getAllInboxMessages();

                // Convert the result to a string
                String messagesString = (inboxMessages != null) ? inboxMessages.toString() : "No Inbox Messages Found";

                // Display the full content in an AlertDialog
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Inbox Messages")
                        .setMessage(messagesString)
                        .setPositiveButton("OK", null)
                        .show();

                // Optionally log the full content in Logcat for debugging
                Log.v("InboxMessagesgetAllunits", messagesString);
            }
        });



        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Name", "Native Display 2");
                prodViewedAction.put("Category", "Mens Accessories");
                prodViewedAction.put("Price", 59.99);
                prodViewedAction.put("Date", new java.util.Date());

                clevertapDefaultInstance.pushEvent("Add To Cart", prodViewedAction);
            }
        });

        requestInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clevertapDefaultInstance.pushEvent("sample_event");
            }
        });

        setIdentityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, SetIdentityActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        productViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Name", "Native Display 1");
                prodViewedAction.put("Category", "Mens Accessories");
                prodViewedAction.put("Price", 59.99);
                prodViewedAction.put("Date", new java.util.Date());

                clevertapDefaultInstance.pushEvent("Add To Cart", prodViewedAction);
            }
        });

//        chargeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                numbersRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        // Iterate through the numbers and find the first available one
//                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                            String value = childSnapshot.getValue(String.class);
//                            if (value == null || value.isEmpty()) {
//                                // Found an available number, assign it to the user
//                                String numberKey = childSnapshot.getKey();
//                                couponNumber = String.valueOf(numberKey);
//
//                                // Update the database to mark the number as used
//                                numbersRef.child(numberKey).setValue(couponNumber);
//
//
//
//                                HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
//                                chargeDetails.put("Amount", 3000);
//                                chargeDetails.put("Payment Mode", "Credit card");
//                                chargeDetails.put("Charged ID", 24052013);
//                                chargeDetails.put("CouponNo",couponNumber);
//
//                                HashMap<String, Object> item1 = new HashMap<String, Object>();
//                                item1.put("Product category", "books");
//                                item1.put("Book name", "The Millionaire next door");
//                                item1.put("Quantity", 1);
//
//                                HashMap<String, Object> item2 = new HashMap<String, Object>();
//                                item2.put("Product category", "books");
//                                item2.put("Book name", "Achieving inner zen");
//                                item2.put("Quantity", 1);
//
//                                HashMap<String, Object> item3 = new HashMap<String, Object>();
//                                item3.put("Product category", "books");
//                                item3.put("Book name", "Chuck it, let's do it");
//                                item3.put("Quantity", 5);
//
//                                ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
//                                items.add(item1);
//                                items.add(item2);
//                                items.add(item3);
//
//                                clevertapDefaultInstance.pushChargedEvent(chargeDetails,items);
//
//                                return;
//                            }
//                        }
//
//                        // If all numbers are used, display a message to the user
//                        Toast.makeText(MainPageActivity.this, "No more numbers available", Toast.LENGTH_SHORT).show();
//                        HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
//                        chargeDetails.put("Amount", 3000);
//                        chargeDetails.put("Payment Mode", "Credit card");
//                        chargeDetails.put("Charged ID", 24052013);
//
//                        HashMap<String, Object> item1 = new HashMap<String, Object>();
//                        item1.put("Product category", "books");
//                        item1.put("Book name", "The Millionaire next door");
//                        item1.put("Quantity", 1);
//
//                        HashMap<String, Object> item2 = new HashMap<String, Object>();
//                        item2.put("Product category", "books");
//                        item2.put("Book name", "Achieving inner zen");
//                        item2.put("Quantity", 1);
//
//                        HashMap<String, Object> item3 = new HashMap<String, Object>();
//                        item3.put("Product category", "books");
//                        item3.put("Book name", "Chuck it, let's do it");
//                        item3.put("Quantity", 5);
//
//                        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
//                        items.add(item1);
//                        items.add(item2);
//                        items.add(item3);
//
//                        clevertapDefaultInstance.pushChargedEvent(chargeDetails,items);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        // Handle error
//                        Toast.makeText(MainPageActivity.this, "Error fetching number", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        chargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                chargeDetails.put("Amount", 3000);
                chargeDetails.put("Payment Mode", "Credit card");
                chargeDetails.put("Charged ID", 24052013);

                HashMap<String, Object> item1 = new HashMap<String, Object>();
                item1.put("Product category", "books");
                item1.put("Book name", "The Millionaire next door");
                item1.put("Quantity", 1);

                HashMap<String, Object> item2 = new HashMap<String, Object>();
                item2.put("Product category", "books");
                item2.put("Book name", "Achieving inner zen");
                item2.put("Quantity", 1);

                HashMap<String, Object> item3 = new HashMap<String, Object>();
                item3.put("Product category", "books");
                item3.put("Book name", "Chuck it, let's do it");
                item3.put("Quantity", 5);

                ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                items.add(item1);
                items.add(item2);
                items.add(item3);

                clevertapDefaultInstance.pushChargedEvent(chargeDetails,items);
            }
        });

        otherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, OtherActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }

    @Override
    public void inboxDidInitialize() {
        appInboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> tabs = new ArrayList<>();
                tabs.add("Promotions");
                tabs.add("Offers");
                tabs.add("Others");//We support upto 2 tabs only. Additional tabs will be ignored

                CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
                styleConfig.setFirstTabTitle("First Tab");
                styleConfig.setTabs(tabs);//Do not use this if you don't want to use tabs
                styleConfig.setTabBackgroundColor("#FF0000");
                styleConfig.setSelectedTabIndicatorColor("#0000FF");
                styleConfig.setSelectedTabColor("#0000FF");
                styleConfig.setUnselectedTabColor("#FFFFFF");
                styleConfig.setBackButtonColor("#FF0000");
                styleConfig.setNavBarTitleColor("#FF0000");
                styleConfig.setNavBarTitle("MY INBOX");
                styleConfig.setNavBarColor("#FFFFFF");
                styleConfig.setInboxBackgroundColor("#ADD8E6");
                clevertapDefaultInstance.showAppInbox(styleConfig);
            }
        });
    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        LinearLayout parentLayout = findViewById(R.id.linear1);

        // Clear existing views to remove any previously loaded content
        parentLayout.removeAllViews();

        // Fetch the latest display units using CleverTap's utility method, if available
        ArrayList<CleverTapDisplayUnit> latestUnits = CleverTapAPI.getDefaultInstance(this).getAllDisplayUnits();
        if (latestUnits != null) {
            units = latestUnits;
        }

        // Render new display units
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
    public void inboxMessagesDidUpdate() {

    }


}
