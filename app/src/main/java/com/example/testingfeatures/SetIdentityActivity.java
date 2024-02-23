package com.example.testingfeatures;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.HashMap;

public class SetIdentityActivity extends AppCompatActivity {
    Button setIdentity;
    EditText usernameEditText;
    String username;
    CleverTapAPI clevertapDefaultInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_identity);
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        username = getIntent().getStringExtra("username");
        setIdentity = findViewById(R.id.identity_update_button);
        usernameEditText = findViewById(R.id.username_edit_text);

        setIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String identity = usernameEditText.getText().toString();

                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Identity", identity);

                clevertapDefaultInstance.pushProfile(profileUpdate);

                Intent intent = new Intent(SetIdentityActivity.this, MainPageActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
