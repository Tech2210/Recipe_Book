package com.example.recipebook;


import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UserProfileActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextDietaryPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDietaryPreferences = findViewById(R.id.editTextDietaryPreferences);
    }
}

