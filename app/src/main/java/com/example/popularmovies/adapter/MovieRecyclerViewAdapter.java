package com.example.popularmovies.adapter;

import android.content.Context;
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
import java.util.List;

/**
 * {@link MovieRecyclerViewAdapter} exposes a list of movies to a
 * {@link androidx.recyclerview.widget.RecyclerView}
 */

public class MovieRecyclerViewAdapter extends
        RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieRecyclerViewHolder> {

    private static final String TAG = MovieRecyclerViewAdapter.class.getSimpleName();
    private List<? extends Movie> mMovieArrayList;
    private LayoutInflater mInflater;
    private Context context;
    private final MovieAdapterOnItemClick mClickHandler;


    public MovieRecyclerViewAdapter(Context context, MovieAdapterOnItemClick mClickHandler, final List<? extends Movie> newMovieArrayList) {
        mInflater = LayoutInflater.from(context);
        this.mClickHandler = mClickHandler;
        this.context = context;
        this.mMovieArrayList = newMovieArrayList;
    }

    @NonNull
    @Override
    public MovieRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView =  mInflater.inflate(R.layout.list_item, parent, false);
        return new MovieRecyclerViewHolder(rootView, this);
    }

    public interface MovieAdapterOnItemClick {
            void onItemClick(Movie Movie);
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

        holder.bindMovie(position);

    }

    /**
     * This function simply returns the number of items to display.
     *
     * @return The number of items available in our movie list
     */
    @Override
    public int getItemCount() {
        if (mMovieArrayList == null) return 0;
        return mMovieArrayList.size();
    }

    /**
     * Cache of the children views for a movie list item.
     */
    public class MovieRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_Poster;
        Movie movie;

        MovieRecyclerViewHolder(@NonNull View itemView, MovieRecyclerViewAdapter adapter) {
            super(itemView);
            iv_Poster =  itemView.findViewById(R.id.iv_movie_backdrop);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickHandler.onItemClick(movie);
        }

        void bindMovie(int index){
            movie = mMovieArrayList.get(index);

        }
    }














}
