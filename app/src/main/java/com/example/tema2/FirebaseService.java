package com.example.tema2;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService {
    private final DatabaseReference reference;
    private static FirebaseService firebaseService;

    private FirebaseService(){
        reference = FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseService getInstance(){
        if(firebaseService == null){
            synchronized (FirebaseService.class){
                if(firebaseService == null){
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }

    public void insert(FavouriteActors favouriteActors){
        if(favouriteActors == null || favouriteActors.getId()!=null){
            return;
        }
        String id = reference.push().getKey();
        favouriteActors.setId(id);
        reference.child(favouriteActors.getId()).setValue(favouriteActors);
    }

    public void insertDirector(FavouriteDirector favouriteDirector){
        if(favouriteDirector == null || favouriteDirector.getId()!=null){
            return;
        }
        String id = reference.push().getKey();
        favouriteDirector.setId(id);
        reference.child(favouriteDirector.getId()).setValue(favouriteDirector);
    }

    public void update(FavouriteActors favouriteActors){
        if(favouriteActors == null || favouriteActors.getId()==null){
            return;
        }
        reference.child(favouriteActors.getId()).setValue(favouriteActors);
    }

    public void updateDirector(FavouriteDirector favouriteDirector){
        if(favouriteDirector == null || favouriteDirector.getId()==null){
            return;
        }
        reference.child(favouriteDirector.getId()).setValue(favouriteDirector);
    }

    public void delete(FavouriteActors favouriteActors){
        if(favouriteActors == null || favouriteActors.getId()==null){
            return;
        }
        reference.child(favouriteActors.getId()).removeValue();
    }

    public void deleteDirector(FavouriteDirector favouriteDirector){
        if(favouriteDirector == null || favouriteDirector.getId()==null){
            return;
        }
        reference.child(favouriteDirector.getId()).removeValue();
    }

    public void addFavouriteActorListener(Callback<List<FavouriteActors>> callback){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FavouriteActors> favouriteActors = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    FavouriteActors favouriteActor = data.getValue(FavouriteActors.class);
                    if(favouriteActor!=null){
                        favouriteActors.add(favouriteActor);
                    }
                }
                callback.runOnUI(favouriteActors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase","Actorul nu este disponibil");
            }
        });
    }

    public void addFavouriteDirectorListener(Callback<List<FavouriteDirector>> callback){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FavouriteDirector> favouriteDirectors = new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    FavouriteDirector favouriteDirector = data.getValue(FavouriteDirector.class);
                    if(favouriteDirectors!=null){
                        favouriteDirectors.add(favouriteDirector);
                    }
                }
                callback.runOnUI(favouriteDirectors);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebase1","Regizorul nu este disponibil");
            }
        });
    }
}
