package com.example.tema2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SectionIndexer;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivityAddHistory extends AppCompatActivity {

    EditText etMovieTitleHistory;
    EditText etReleaseDateHistory;
    EditText etGenreHistory;
    RadioGroup rgEnjoy;
    Button btnAddMovieHistory;
    Boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_add_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etMovieTitleHistory = findViewById(R.id.etMovieTitleHistory);
        etReleaseDateHistory = findViewById(R.id.etReleaseDateHistory);
        etGenreHistory = findViewById(R.id.etGenreHistory);
        rgEnjoy = findViewById(R.id.rgEnjoy);
        btnAddMovieHistory = findViewById(R.id.btnAddMovieHistory);

        Intent editIntent = getIntent();
        if(editIntent.hasExtra("editHistory")){
            isEditing = true;
            MovieInHistory movieInHistory = (MovieInHistory) editIntent.getSerializableExtra("editHistory");
            etMovieTitleHistory.setText(movieInHistory.getTitleHistory());
            etReleaseDateHistory.setText( new SimpleDateFormat("dd/MM/yyyy").format(movieInHistory.getReleaseDateHistory()));
            etGenreHistory.setText(movieInHistory.getGenreHistory());
            switch (movieInHistory.getEnjoy().toString()){
                case "Yes":{
                    rgEnjoy.check(R.id.rbYes);
                    break;
                }

                case "No":{
                    rgEnjoy.check(R.id.rbNo);
                    break;
                }
            }
        }

        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        String token = sharedPreferences.getString("tokenHistory", "Valoare default");
        String tokenReceived = sharedPreferences.getString("tokenHistoryReceived", "Valoare default");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, tokenReceived, Toast.LENGTH_SHORT).show();

        btnAddMovieHistory.setOnClickListener(view->{
            String titleHistory = etMovieTitleHistory.getText().toString();
            Date releaseDateHistory = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                releaseDateHistory = sdf.parse(etReleaseDateHistory.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String genreHistory = etGenreHistory.getText().toString();
            RadioButton rbEnjoySelected = findViewById(rgEnjoy.getCheckedRadioButtonId());
            Enjoy enjoy = Enjoy.valueOf(rbEnjoySelected.getText().toString());

            MovieInHistory movieInHistory = new MovieInHistory(titleHistory,releaseDateHistory,genreHistory,enjoy);

            Intent intent = getIntent();
            if(isEditing){
                intent.putExtra("editHistory", movieInHistory);
                isEditing = false;
            } else{
                intent.putExtra("history", movieInHistory);
            }

            Toast.makeText(this,"Movie added in history", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}