package com.example.tema2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivityActor extends AppCompatActivity {
    private EditText etDenumire;
    private EditText etVarsta;
    private Button btnGolireDate;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabDelete;
    private ListView lvActori;
    private List<FavouriteActors> favouriteActorsList = new ArrayList<>();
    private FirebaseService firebaseService;
    private int indexActorSelectat = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_actor);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponente();
        firebaseService = FirebaseService.getInstance();
        firebaseService.addFavouriteActorListener(dataChangeCallback());
    }

    private Callback<List<FavouriteActors>> dataChangeCallback() {
        return rezultat -> {
            favouriteActorsList.clear();
            for (FavouriteActors actor : rezultat) {
                if (actor.getName() != null && !actor.getName().isEmpty()) {
                    favouriteActorsList.add(actor);
                }
            }
            ArrayAdapter<FavouriteActors> adapter = (ArrayAdapter<FavouriteActors>) lvActori.getAdapter();
            adapter.notifyDataSetChanged();
            golireText();
        };
    }


    private void initComponente() {
        etDenumire = findViewById(R.id.etDenumire);
        etVarsta = findViewById(R.id.etVarsta);
        btnGolireDate = findViewById(R.id.btnGolireDate);
        fabSave = findViewById(R.id.fabSave);
        fabDelete = findViewById(R.id.fabDelete);
        lvActori = findViewById(R.id.lvActori);
        ArrayAdapter<FavouriteActors> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, favouriteActorsList);
        lvActori.setAdapter(adapter);
        btnGolireDate.setOnClickListener(golireDateEventListener());
        fabSave.setOnClickListener(saveEventListener());
        fabDelete.setOnClickListener(deleteEventListener());
        lvActori.setOnItemClickListener(FavouriteActorSelectatEventListener());
    }

    private AdapterView.OnItemClickListener FavouriteActorSelectatEventListener() {
        return (adapterView, view, position, param) -> {
            indexActorSelectat = position;
            FavouriteActors favouriteActor = favouriteActorsList.get(position);
            etDenumire.setText(favouriteActor.getName());
            etVarsta.setText(String.valueOf(favouriteActor.getAge()));
        };
    }

    private View.OnClickListener deleteEventListener() {
        return view -> {
            if (indexActorSelectat != -1) {
                firebaseService.delete(favouriteActorsList.get(indexActorSelectat));
            }
        };
    }

    private View.OnClickListener saveEventListener() {
        return view -> {
            if (validare()) {
                if (indexActorSelectat == -1) {
                    FavouriteActors favouriteActor = actualizareFavoriteActorFromUI(null);
                    firebaseService.insert(favouriteActor);
                } else {
                    FavouriteActors favouriteActor = actualizareFavoriteActorFromUI(favouriteActorsList.get(indexActorSelectat).getId());
                    firebaseService.update(favouriteActor);
                }
            }

        };
    }

    private FavouriteActors actualizareFavoriteActorFromUI(String id) {
        FavouriteActors favouriteActor = new FavouriteActors();
        favouriteActor.setId(id);
        favouriteActor.setName(etDenumire.getText().toString());
        favouriteActor.setAge(Integer.valueOf(etVarsta.getText().toString()));
        return favouriteActor;
    }

    private boolean validare() {
        if (etDenumire.getText() == null || etDenumire.getText().toString().isEmpty() || etVarsta.getText() == null || etVarsta.getText().toString().isEmpty()) {
            Toast.makeText(this, "Validarea nu a trecut", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private View.OnClickListener golireDateEventListener() {
        return view -> golireText();
    }

    private void golireText() {
        etDenumire.setText(null);
        etVarsta.setText(null);
        indexActorSelectat = -1;
    }
}