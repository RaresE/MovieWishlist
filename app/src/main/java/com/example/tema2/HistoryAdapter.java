package com.example.tema2;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<MovieInHistory> {
    private Context context;
    private List<MovieInHistory> moiesHistory;
    private int layoutId;
    private LayoutInflater inflater;

    public HistoryAdapter(@NonNull Context context, int resource, @NonNull List<MovieInHistory> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.moiesHistory = objects;
        this.layoutId = resource;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(layoutId,parent,false);
        MovieInHistory movieInHistory = moiesHistory.get(position);
        TextView tvMovieTitleHistoryView2 = view.findViewById(R.id.tvMovieTitleHistoryView2);
        TextView tvReleaseDateHistoryView2 = view.findViewById(R.id.tvReleaseDateHistoryView2);
        TextView tvGenreHistoryView2 = view.findViewById(R.id.tvGenreHistoryView2);
        TextView tvEnjoyView2 = view.findViewById(R.id.tvEnjoyView2);
        TextView tvIdHistory = view.findViewById(R.id.tvIdHistory);

        tvMovieTitleHistoryView2.setText(movieInHistory.getTitleHistory());
        tvReleaseDateHistoryView2.setText(new SimpleDateFormat("dd/MM/yyyy").format(movieInHistory.getReleaseDateHistory()));
        tvGenreHistoryView2.setText(movieInHistory.getGenreHistory());
        tvEnjoyView2.setText(String.valueOf(movieInHistory.getEnjoy()));
        if(movieInHistory.getEnjoy() == Enjoy.Yes)
            tvEnjoyView2.setBackgroundColor(Color.GREEN);
        else
            tvEnjoyView2.setBackgroundColor(Color.RED);
        tvIdHistory.setText(String.valueOf(movieInHistory.getId()));

        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(tvGenreHistoryView2, "alpha", 0f, 1f);
        fadeIn.setDuration(3000);
        fadeIn.start();

        return view;
    }
}
