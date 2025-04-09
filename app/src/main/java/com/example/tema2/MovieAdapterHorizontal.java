package com.example.tema2;

import android.adservices.adselection.TestAdSelectionManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

public class MovieAdapterHorizontal extends ArrayAdapter<Movie> {
    private Context context;
    private List<Movie> movies;
    private int layoutId;
    private LayoutInflater inflater;

    public MovieAdapterHorizontal(@NonNull Context context, int resource, @NonNull List<Movie> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.movies = objects;
        this.layoutId = resource;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(layoutId,parent,false);
        Movie movie = movies.get(position);
        TextView tvMovieTitleHorizontal = view.findViewById(R.id.tvMovieTitleHorizontal);
        TextView tvReleaseDateHorizontal = view.findViewById(R.id.tvReleaseDateHorizontal);
        TextView tvGenreHorizontal = view.findViewById(R.id.tvGenreHorizontal);
        TextView tvPlatformHorizontal = view.findViewById(R.id.tvPlatformHorizontal);

        tvMovieTitleHorizontal.setText(movie.getTitle());
        tvReleaseDateHorizontal.setText(new SimpleDateFormat("dd/MM/yyyy").format(movie.getReleaseDate()));
        tvGenreHorizontal.setText(movie.getGenre());
        tvPlatformHorizontal.setText(movie.getStreamingPlatform());

        if(movie.getGenre().equals("Comedy"))
            tvGenreHorizontal.setBackgroundColor(Color.YELLOW);
        else
            if(movie.getGenre().equals("Drama"))
                tvGenreHorizontal.setBackgroundColor(Color.RED);
            else
                if(movie.getGenre().equals("Action"))
                    tvGenreHorizontal.setBackgroundColor(Color.BLUE);
                else
                    if(movie.getGenre().equals("Adventure"))
                        tvGenreHorizontal.setBackgroundColor(Color.GREEN);

        return view;
    }
}
