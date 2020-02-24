package com.example.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.popularmovies.R;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.ui.MovieDetailActivity;

import java.util.ArrayList;

/**
 * {@link MovieRecyclerViewAdapter} exposes a list of movies to a
 * {@link androidx.recyclerview.widget.RecyclerView}
 */

public class MovieRecyclerViewAdapter extends
        RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieRecyclerViewHolder> {

    private static final String TAG = MovieRecyclerViewAdapter.class.getSimpleName();
    private ArrayList<Movie> mMovieArrayList;
    private LayoutInflater mInflater;
    private Context context;


    public MovieRecyclerViewAdapter(Activity context, ArrayList<Movie> movieArrayList) {
        mInflater = LayoutInflater.from(context);
        mMovieArrayList = movieArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView =  mInflater.inflate(R.layout.list_item, parent, false);
        return new MovieRecyclerViewHolder(rootView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRecyclerViewHolder holder, final int position) {
        final Movie movie = mMovieArrayList.get(position);
        if(movie.getPoster_path() != null){
            String url = "https://image.tmdb.org/t/p/w342" + movie.getPoster_path();
            Glide.with(context).load(url).centerCrop().into(holder.iv_Poster);
        }else{
            Log.d(TAG, "Unable to download poster image");
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param view The View that was clicked
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            }
        });
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieRecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_Poster;

        public MovieRecyclerViewHolder(@NonNull View itemView, MovieRecyclerViewAdapter adapter) {
            super(itemView);
            iv_Poster =  itemView.findViewById(R.id.iv_movie_backdrop);

        }
    }

    @Override
    public int getItemCount() {
        return mMovieArrayList.size();
    }


}
