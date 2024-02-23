package com.example.testingfeatures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.Date;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    EditText usernameEditText,nameEditText;
    Button loginButton;
    CleverTapAPI clevertapDefaultInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        usernameEditText = findViewById(R.id.mobile_no_edit_text);
        nameEditText = findViewById(R.id.name_edit_text);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobileno = usernameEditText.getText().toString();
                String name = nameEditText.getText().toString();

                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Phone", mobileno);
                profileUpdate.put("Name", name);

                clevertapDefaultInstance.onUserLogin(profileUpdate);

                Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
                intent.putExtra("username", name);
                startActivity(intent);
            }
        });

    }
}

