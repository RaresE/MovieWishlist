package com.example.tema2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FeedbackPage extends AppCompatActivity {

    private Button btnSendFeedback;
    private Button btnAddActor;
    private Button btnAddDirector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvMovies), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSendFeedback = findViewById(R.id.btnSendFeedback);
        btnAddActor = findViewById(R.id.btnAddActor);
        btnAddDirector = findViewById(R.id.btnAddDirector);

        btnSendFeedback.setOnClickListener(v->{
            finish();
            Toast.makeText(this,"Feedback sent!",Toast.LENGTH_SHORT).show();
        });

        btnAddActor.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivityActor.class);
            startActivity(intent);
        });

        btnAddDirector.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivityDirector.class);
            startActivity(intent);
        });
    }
}