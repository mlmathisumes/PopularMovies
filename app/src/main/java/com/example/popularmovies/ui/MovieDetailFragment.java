package com.example.popularmovies.ui;


import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.deanwild.flowtextview.FlowTextView;

public class MovieDetailFragment extends Fragment {

    public static final String TAG = MovieDetailFragment.class.getSimpleName();

    private Movie movie;
    private ImageView imageView;
    private ImageView ivMoviePoster;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private FlowTextView flowTextView;
    private TextView tvOriginalTitle;
    private TextView tvReleaseDate;
    private TextView tvRating;
    public static final String PATTERN = "MM/DD/YYYY";

    public MovieDetailFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movie_desc, container, false);
        imageView = rootView.findViewById(R.id.iv_movie_backdrop);
        collapsingToolbarLayout = rootView.findViewById(R.id.collapsingTL);
        toolbar = rootView.findViewById(R.id.toolbar);
        ivMoviePoster = rootView.findViewById(R.id.iv_movie_poster);
        flowTextView = rootView.findViewById(R.id.ftv);
        tvOriginalTitle = rootView.findViewById(R.id.tv_original_title);
        tvRating = rootView.findViewById(R.id.tv_rating);
        tvReleaseDate = rootView.findViewById(R.id.tv_release_date);
        toolbar = rootView.findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Bundle bundle = this.getArguments();
        movie = bundle.getParcelable("movie");

        if(movie.getPoster_path() != null){
            String url = "https://image.tmdb.org/t/p/w185" + movie.getPoster_path();
            Glide.with(getActivity()).load(url).into(ivMoviePoster);
            collapsingToolbarLayout.setTitle(movie.getTitle());
        }if(movie.getBackdrop_path() != null){
            String url = "https://image.tmdb.org/t/p/w342" + movie.getBackdrop_path();
            Glide.with(getActivity()).load(url).into(imageView);
            String content = movie.getOverview();
            Spanned html = Html.fromHtml(content);
            flowTextView.setTextSize(60);
            flowTextView.setColor(R.color.colorContent);
            flowTextView.setText(html);

        }else{
            Log.d(TAG, "Unable to download poster image");
        }
        tvOriginalTitle.setText(movie.getOriginal_title());
        tvRating.setText(movie.getVote_average() + "/10");

        try {
            Date date = new SimpleDateFormat(PATTERN).parse(movie.getRelease_date());
            tvReleaseDate.setText(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return  rootView;
    }
}
