package com.example.tema2;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private Context context;
    private List<Movie> movies;
    private int layoutId;
    private LayoutInflater inflater;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<Movie> objects, LayoutInflater inflater) {
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
        TextView tvMovieTitle = view.findViewById(R.id.tvMovieTitle);
        TextView tvReleaseDate = view.findViewById(R.id.tvReleaseDate);
        TextView tvGenre = view.findViewById(R.id.tvGenre);
        TextView tvPlatform = view.findViewById(R.id.tvPlatform);
        TextView tvId = view.findViewById(R.id.tvId);

        tvMovieTitle.setText(movie.getTitle());
        tvReleaseDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(movie.getReleaseDate()));
        tvGenre.setText(movie.getGenre());
        tvPlatform.setText(movie.getStreamingPlatform());
        tvId.setText(String.valueOf(movie.getId()));

        String compareDate = "01/11/2024";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date compareDate1 = dateFormat.parse(compareDate);
            if(movie.getReleaseDate().before(compareDate1))
                tvReleaseDate.setTextColor(Color.RED);
            else
                tvReleaseDate.setTextColor(Color.GREEN);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return view;
    }
}
