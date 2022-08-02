package com.example.movieapp.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movieapp.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title,duration,release_date;
    ImageView imageView;
    RatingBar ratingBar;


    OnMovieListener onMovieListener;


    public MovieViewHolder(@NonNull View itemView,OnMovieListener onMovieListener) {
        super(itemView);
        this.onMovieListener = onMovieListener;
        title = itemView.findViewById(R.id.movie_title);
        duration = itemView.findViewById(R.id.movie_duration);
        release_date = itemView.findViewById(R.id.movie_category);
        imageView = itemView.findViewById(R.id.movie_image);
        ratingBar = itemView.findViewById(R.id.rating_bar);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getBindingAdapterPosition());
    }
}
