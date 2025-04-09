package com.example.tema2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class    MainActivityHistory extends AppCompatActivity {

    private ListView lvHistory;
    private List<MovieInHistory> moviesHistory = new ArrayList<>();
    private ActivityResultLauncher<Intent> launcher;
    private Button btnAddHistory;
    private int position;
    private static final String jsonUrl = "https://www.jsonkeeper.com/b/YDPE";
    private MovieInHistoryDB dbInstanceHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbInstanceHistory = MovieInHistoryDB.getInstance(getApplicationContext());
        initComponente();
        incarcareMoviesInHistoryDinRetea();

        //lvHistory = findViewById(R.id.lvHistory);

        lvHistory.setOnItemClickListener((adapterView, view, position, l)->{
            this.position = position;
            Intent intent = new Intent(getApplicationContext(), MainActivityAddHistory.class);
            intent.putExtra("editHistory", moviesHistory.get(position));
            launcher.launch(intent);
        });

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result-> {
            if(result.getData().hasExtra("history")){
                Intent intent = result.getData();
                MovieInHistory movieInHistory = (MovieInHistory) intent.getSerializableExtra("history");
                if(movieInHistory!=null) {
                    //moviesHistory.add(movieInHistory);
                    dbInstanceHistory.getMovieInHistoryDAO().insertMovieInHistory(movieInHistory);
                }
                moviesHistory.clear();
                moviesHistory = dbInstanceHistory.getMovieInHistoryDAO().getAllMoviesInHistory();
                //ArrayAdapter<MovieInHistory> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,moviesHistory);
                HistoryAdapter adapter = new HistoryAdapter(getApplicationContext(),R.layout.view2_verticalhistory,moviesHistory,getLayoutInflater());
                lvHistory.setAdapter(adapter);

                lvHistory.setOnItemLongClickListener((parent, view, position1, id) -> {
                    MovieInHistory deletedMovieInHistory = moviesHistory.get(position1);
                    moviesHistory.remove(position1);
                    dbInstanceHistory.getMovieInHistoryDAO().deleteMovieInHistory(deletedMovieInHistory);
                    adapter.notifyDataSetChanged();
                    return false;
                });

            } else if(result.getData().hasExtra("editHistory")) {
                Intent intent = result.getData();
                MovieInHistory editedMovieInHistory = (MovieInHistory) intent.getSerializableExtra("editHistory");
                if(editedMovieInHistory!=null){
                    MovieInHistory movieInHistory = moviesHistory.get(position);
                    movieInHistory.setTitleHistory(editedMovieInHistory.getTitleHistory());
                    movieInHistory.setReleaseDateHistory(editedMovieInHistory.getReleaseDateHistory());
                    movieInHistory.setGenreHistory(editedMovieInHistory.getGenreHistory());
                    movieInHistory.setEnjoy(editedMovieInHistory.getEnjoy());

                    HistoryAdapter adapter = (HistoryAdapter) lvHistory.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("local", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tokenHistory","TokenHistory");
        editor.putString("tokenHistoryReceived","TokenHistoryReceived");
        editor.apply();

        btnAddHistory = findViewById(R.id.btnAddHistory);
        btnAddHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivityAddHistory.class);
            launcher.launch(intent);
        });
    }

    private void incarcareMoviesInHistoryDinRetea(){
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
        moviesHistory.addAll(MovieInHistoryParser.parsareJson(json));
        moviesHistory.forEach(movieInHistory -> {
            dbInstanceHistory.getMovieInHistoryDAO().insertMovieInHistory(movieInHistory);
        });
        moviesHistory = dbInstanceHistory.getMovieInHistoryDAO().getAllMoviesInHistory();
        HistoryAdapter adapter = (HistoryAdapter) lvHistory.getAdapter();
        adapter.notifyDataSetChanged();
    }

    private void initComponente(){
        lvHistory = findViewById(R.id.lvHistory);
        HistoryAdapter adapter = new HistoryAdapter(getApplicationContext(),R.layout.view2_verticalhistory,moviesHistory,getLayoutInflater());
        lvHistory.setAdapter(adapter);
    }
}