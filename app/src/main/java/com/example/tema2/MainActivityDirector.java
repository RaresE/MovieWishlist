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

public class MainActivityDirector extends AppCompatActivity {
    private EditText etNumeDirector;
    private EditText etVarstaDirector;
    private Button btnGolireDateDirector;
    private FloatingActionButton fabSaveDirector;
    private FloatingActionButton fabDeleteDirector;
    private ListView lvDirector;
    private List<FavouriteDirector> favouriteDirectorList = new ArrayList<>();
    private FirebaseService firebaseServiceDirector;
    private int indexDirectorSelectat = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_director);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponente();
        firebaseServiceDirector = FirebaseService.getInstance();
        firebaseServiceDirector.addFavouriteDirectorListener(dataChangeCallback1());
    }

    private Callback<List<FavouriteDirector>> dataChangeCallback1() {
        return rezultat -> {
            favouriteDirectorList.clear();
            for (FavouriteDirector director : rezultat) {
                if (director.getDenumire() != null && !director.getDenumire().isEmpty()) {
                    favouriteDirectorList.add(director);
                }
            }

            ArrayAdapter<FavouriteDirector> adapter = (ArrayAdapter<FavouriteDirector>) lvDirector.getAdapter();
            adapter.notifyDataSetChanged();
            golireText();
        };
    }


    private void initComponente() {
        etNumeDirector = findViewById(R.id.etNumeDirector);
        etVarstaDirector = findViewById(R.id.etVarstaDirector);
        btnGolireDateDirector = findViewById(R.id.btnGolireDateDirector);
        fabSaveDirector = findViewById(R.id.fabSaveDirector);
        fabDeleteDirector = findViewById(R.id.fabDeleteDirector);
        lvDirector = findViewById(R.id.lvDirector);
        ArrayAdapter<FavouriteDirector> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, favouriteDirectorList);
        lvDirector.setAdapter(adapter);
        btnGolireDateDirector.setOnClickListener(golireDateEventListener());
        fabSaveDirector.setOnClickListener(saveEventListener());
        fabDeleteDirector.setOnClickListener(deleteEventListener());
        lvDirector.setOnItemClickListener(FavouriteDirectorSelectatEventListener());
    }

    private View.OnClickListener golireDateEventListener() {
        return view -> golireText();
    }

    private void golireText() {
        etNumeDirector.setText(null);
        etVarstaDirector.setText(null);
        indexDirectorSelectat = -1;
    }

    private View.OnClickListener saveEventListener() {
        return view -> {
            if (validare()) {
                if (indexDirectorSelectat == -1) {
                    FavouriteDirector favouriteDirector = actualizareFavoriteDirectorFromUI(null);
                    firebaseServiceDirector.insertDirector(favouriteDirector);
                } else {
                    FavouriteDirector favouriteDirector = actualizareFavoriteDirectorFromUI(favouriteDirectorList.get(indexDirectorSelectat).getId());
                    firebaseServiceDirector.updateDirector(favouriteDirector);
                }
            }

        };
    }

    private FavouriteDirector actualizareFavoriteDirectorFromUI(String id) {
        FavouriteDirector favouriteDirector = new FavouriteDirector();
        favouriteDirector.setId(id);
        favouriteDirector.setDenumire(etNumeDirector.getText().toString());
        favouriteDirector.setAge(Integer.valueOf(etVarstaDirector.getText().toString()));
        return favouriteDirector;
    }

    private boolean validare() {
        if (etNumeDirector.getText() == null || etNumeDirector.getText().toString().isEmpty() || etVarstaDirector.getText() == null || etVarstaDirector.getText().toString().isEmpty()) {
            Toast.makeText(this, "Validarea nu a trecut", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private View.OnClickListener deleteEventListener() {
        return view -> {
            if (indexDirectorSelectat != -1) {
                firebaseServiceDirector.deleteDirector(favouriteDirectorList.get(indexDirectorSelectat));
            }
        };
    }

    private AdapterView.OnItemClickListener FavouriteDirectorSelectatEventListener() {
        return (adapterView, view, position, param) -> {
            indexDirectorSelectat = position;
            FavouriteDirector favouriteDirector = favouriteDirectorList.get(position);
            etNumeDirector.setText(favouriteDirector.getDenumire());
            etVarstaDirector.setText(String.valueOf(favouriteDirector.getAge()));
        };
    }
}