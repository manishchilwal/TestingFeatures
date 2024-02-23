package com.example.testingfeatures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.Date;
import java.util.HashMap;

public class UpdateProfileActivity extends AppCompatActivity {
    EditText emailEditText,languageEditText,addressEditText;
    Button updateButton;
    CleverTapAPI clevertapDefaultInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        emailEditText = findViewById(R.id.email_edit_text);
        languageEditText = findViewById(R.id.language_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        updateButton = findViewById(R.id.update_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String language = languageEditText.getText().toString();
                String address = addressEditText.getText().toString();

                String username = getIntent().getStringExtra("username");

                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Email", email);
                profileUpdate.put("Address",address);
                profileUpdate.put("Language", language);

                clevertapDefaultInstance.pushProfile(profileUpdate);

                Intent intent = new Intent(UpdateProfileActivity.this, MainPageActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}

