package com.example.tema2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivityMeniu extends AppCompatActivity {

    private Button btnLogOut;
    private Button btnMovie;
    private Button btnFeedback;
    private ListView lvMovies;
    private List<Movie> movies = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;
    private TextView tvWelcome;
    private FloatingActionButton fabOpenHistory;
    private  int position;
    private static final String jsonUrl = "https://www.jsonkeeper.com/b/PW24";
    private MovieDB dbInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_meniu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.lvMovies), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbInstance = MovieDB.getInstance(getApplicationContext());
        initComponente();
        incarcareMoviesDinRetea();

        btnLogOut = findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(view->{
            Intent intent = new Intent(getApplicationContext(), MainActivityProfile.class);
            startActivity(intent);
        });

        tvWelcome = findViewById(R.id.tvWelcome);
        Intent intentProfile = getIntent();
        CreateProfile newPofile = (CreateProfile) intentProfile.getSerializableExtra("profile");
        if(newPofile!=null)
        {
            tvWelcome.setText("Welcome "+newPofile.getName()+"!");
        }

        //lvMovies = (ListView) findViewById(R.id.lvMovies);

        lvMovies.setOnItemClickListener((adapterView, view, position, l)->{
            this.position = position;
            Intent intent = new Intent(getApplicationContext(), MainActivityMovie.class);
            intent.putExtra("edit", movies.get(position));
            launcher.launch(intent);
        });

        //MovieDB dbInstance = MovieDB.getInstance(getApplicationContext());

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result-> {
            if(result.getData().hasExtra("addMovie"))
            {
                Intent intent = result.getData();
                Movie movie = (Movie) intent.getSerializableExtra("addMovie");
                if(movie!=null) {
                    //movies.add(movie);
                    dbInstance.getMovieDAO().insertMovie(movie);
                }
                movies.clear();
                movies = dbInstance.getMovieDAO().getAllMovies();
                //ArrayAdapter<Movie> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,movies);
                //Adapter Custom
                //Folosim din functie
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(),R.layout.view1_verticalmovie,movies,getLayoutInflater());
                //MovieAdapterHorizontal adapter = new MovieAdapterHorizontal(getApplicationContext(), R.layout.view3_horizontalmovie, movies, getLayoutInflater());
                lvMovies.setAdapter(adapter);
                Toast.makeText(this,"Movie added",Toast.LENGTH_SHORT).show();

                lvMovies.setOnItemLongClickListener((parent, view, position1, l) -> {
                    Movie deletedMovie = movies.get(position1);
                    movies.remove(position1);
                    dbInstance.getMovieDAO().deleteMovie(deletedMovie);
                    adapter.notifyDataSetChanged();
                    return false;
                });
            }
            else if(result.getData().hasExtra("edit")){
                Intent intent = result.getData();
                Movie editedMovie = (Movie) intent.getSerializableExtra("edit");
                if(editedMovie != null){
                    Movie movie = movies.get(position);
                    movie.setTitle(editedMovie.getTitle());
                    movie.setReleaseDate(editedMovie.getReleaseDate());
                    movie.setGenre(editedMovie.getGenre());
                    movie.setStreamingPlatform(editedMovie.getStreamingPlatform());
                    MovieAdapter adapter = (MovieAdapter) lvMovies.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tokenMovie","TokenMovie");
        editor.apply();

        btnMovie = findViewById(R.id.btnMovie);
        btnMovie.setOnClickListener(v->{
            Intent intent1 = new Intent(getApplicationContext(), MainActivityMovie.class);
            launcher.launch(intent1);
        });

        btnFeedback = findViewById(R.id.btnFeedback);
        btnFeedback.setOnClickListener(v->{
            Intent intent2 = new Intent(getApplicationContext(),FeedbackPage.class);
            startActivity(intent2);
        });

        fabOpenHistory = findViewById(R.id.fabOpenHistory);
        fabOpenHistory.setOnClickListener(view->{
            Intent intent2 = new Intent(getApplicationContext(), MainActivityHistory.class);
            startActivity(intent2);
        });
    }

    private void incarcareMoviesDinRetea(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                HttpsManager manager = new HttpsManager(jsonUrl);
                String json = manager.procesare();
                new Handler(getMainLooper()).post(()->{
                    getJsonFromHttps(json);
                });
            }
        };
        thread.start();
    }

    private void getJsonFromHttps(String json){
        movies.addAll(MovieParser.parsareJson(json));
        //MovieDB dbInstance = MovieDB.getInstance(getApplicationContext());
        movies.forEach(movie -> {
            dbInstance.getMovieDAO().insertMovie(movie);
        });
        movies = dbInstance.getMovieDAO().getAllMovies();
        MovieAdapter adapter = (MovieAdapter) lvMovies.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void initComponente(){
        lvMovies = findViewById(R.id.lvMovies);
        MovieAdapter adapter = new MovieAdapter(getApplicationContext(),R.layout.view1_verticalmovie,movies,getLayoutInflater());
        lvMovies.setAdapter(adapter);
    }
}