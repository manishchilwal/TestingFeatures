package com.example.testingfeatures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.Date;
import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {
    private EditText nameEditText, addressEditText, emailEditText, usernameEditText, phoneEditText;
    Button saveButton;

    CleverTapAPI clevertapDefaultInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        nameEditText = findViewById(R.id.name_edit_text);
        addressEditText = findViewById(R.id.address_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        usernameEditText = findViewById(R.id.username_edit_text);

        saveButton = findViewById(R.id.save_button);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String name = nameEditText.getText().toString().trim();
                String address = addressEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String username = usernameEditText.getText().toString().trim();

                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", name);
                profileUpdate.put("Email", email);
                profileUpdate.put("Phone", phone);
                profileUpdate.put("Address",address);
                profileUpdate.put("Identity",username);
                profileUpdate.put("DOB", new Date());

                profileUpdate.put("MSG-email", false);
                profileUpdate.put("MSG-push", true);
                profileUpdate.put("MSG-sms", false);
                profileUpdate.put("MSG-whatsapp", true);

                clevertapDefaultInstance.onUserLogin(profileUpdate);
                Intent intent = new Intent(SignInActivity.this, MainPageActivity.class);
                intent.putExtra("username", name);
                startActivity(intent);
            }
        });
    }
}


