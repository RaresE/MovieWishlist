package com.example.tema2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class MainActivityMovie extends AppCompatActivity {

    EditText etMovieTitle;
    EditText etReleaseDate;
    EditText etGenre;
    EditText etPlatform;
    Button btnAddMovie;
    Boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvMovies), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etMovieTitle = findViewById(R.id.etMovieTitle);
        etReleaseDate = findViewById(R.id.etReleaseDate);
        etGenre = findViewById(R.id.etGenre);
        etPlatform = findViewById(R.id.etPlatform);
        btnAddMovie = findViewById(R.id.btnAddMovie);

        //edit
        Intent editIntent = getIntent();
        if(editIntent.hasExtra("edit")){
            isEditing = true;
            Movie movie = (Movie) editIntent.getSerializableExtra("edit");
            etMovieTitle.setText(movie.getTitle());
            etReleaseDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(movie.getReleaseDate()));
            etGenre.setText(movie.getGenre());
            etPlatform.setText(movie.getStreamingPlatform());
        }

        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        String token = sharedPreferences.getString("tokenMovie", "Valoare default");
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();

        btnAddMovie.setOnClickListener(v->{
            String movieTitle = etMovieTitle.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date releaseDate = null;
            try {
                 releaseDate = sdf.parse(etReleaseDate.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String genre = etGenre.getText().toString();
            String platform = etPlatform.getText().toString();
            Movie movie = new Movie(movieTitle,releaseDate,genre,platform);

            Intent intent = getIntent();
            if(isEditing) {
                intent.putExtra("edit", movie);
                isEditing = false;
            } else {
                intent.putExtra("addMovie", movie);
            }

            setResult(RESULT_OK, intent);
            finish();
            //Toast.makeText(this,"Movie added!", Toast.LENGTH_SHORT).show();
        });
    }
}