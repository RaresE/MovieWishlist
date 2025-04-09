package com.example.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivityProfile extends AppCompatActivity {

    EditText etName;
    EditText etNumber;
    EditText etEmail;
    Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvMovies), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);
        etEmail = findViewById(R.id.etEmail);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(v->{
            String name = etName.getText().toString();
            int number = Integer.parseInt(etNumber.getText().toString());
            String email = etEmail.getText().toString();
            CreateProfile newProfile = new CreateProfile(name,number,email);

            Intent intent = new Intent(getApplicationContext(), MainActivityMeniu.class);
            intent.putExtra("profile", newProfile);
            startActivity(intent);
            finish();
            Toast.makeText(this,"Profile created successfully!", Toast.LENGTH_SHORT).show();
        });
    }
}