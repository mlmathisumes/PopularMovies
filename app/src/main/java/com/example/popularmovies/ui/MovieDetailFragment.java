package com.example.popularmovies.ui;


import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.popularmovies.viewmodel.DetailViewModelFactory;
import com.example.popularmovies.adapter.ReviewsRecyclerViewAdapter;
import com.example.popularmovies.adapter.TrailerRecyclerViewAdpater;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.R;
import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.Trailer;
import com.example.popularmovies.utils.InjectUtils;
import com.example.popularmovies.viewmodel.DetailViewModel;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.deanwild.flowtextview.FlowTextView;

public class MovieDetailFragment extends Fragment {

    private static final String TAG = MovieDetailFragment.class.getSimpleName();

    private Movie movie;
    private String sortOrder;
    private ImageView imageView;
    private ImageView ivMoviePoster;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private FlowTextView flowTextView;
    private RecyclerView rvTrailer;
    private RecyclerView rvReviews;
    private TrailerRecyclerViewAdpater trailerAdapter;
    private ReviewsRecyclerViewAdapter reviewsAdapter;
    private TextView tvOriginalTitle;
    private TextView tvReleaseDate;
    private TextView tvRating;
    private TextView emptyReviewMessage;
    private TextView emptyTrailersMessage;
    private static final String PATTERN = "YYYY-MM-DD";
    private static final String NEW_FORMAT = "MM/dd/yyyy";
    private Menu menu;
    private DetailViewModel detailViewModel;

    public MovieDetailFragment(){

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = initView(inflater, container);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            movie = bundle.getParcelable(getResources().getString(R.string.movie));
            sortOrder = bundle.getString(getResources().getString(R.string.sort_order ));
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DetailViewModelFactory detailViewModelFactory = InjectUtils.provideDetailViewModelFactory(getActivity().getApplication());
        detailViewModel = ViewModelProviders.of(this, detailViewModelFactory).get(DetailViewModel.class);
        detailViewModel.loadMovie(movie.getId(), sortOrder);
        detailViewModel.getFavorite().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite));
                }else{
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_favorite_border));
                }
            }
        });

        detailViewModel.getTrailerListLiveData().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                if(trailers.size() < 1){
                    showEmptyTrailerMessage();

                }else{
                    showTrailers();
                    ArrayList<Trailer> trailersList = new ArrayList<Trailer>(trailers);
                    trailerAdapter = new TrailerRecyclerViewAdpater(getContext(), trailersList);
                    rvTrailer.setAdapter(trailerAdapter);
                }

            }
        });

        detailViewModel.getReviewListLiveData().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                if(reviews.size() < 1){
                    showEmptyReviewsMessage();
                }else{
                    showReviews();
                    ArrayList<Review> reviewList = new ArrayList<Review>(reviews);
                    reviewsAdapter = new ReviewsRecyclerViewAdapter(getContext(), reviewList);
                    rvReviews.setAdapter(reviewsAdapter);
                }
            }
        });

        setDetailView(movie);
    }

    private void showTrailers() {
        rvTrailer.setVisibility(View.VISIBLE);
        emptyTrailersMessage.setVisibility(View.INVISIBLE);
    }

    private void showEmptyTrailerMessage() {
        rvTrailer.setVisibility(View.INVISIBLE);
        emptyTrailersMessage.setVisibility(View.VISIBLE);

    }

    private void showReviews() {
        rvReviews.setVisibility(View.VISIBLE);
        emptyReviewMessage.setVisibility(View.INVISIBLE);
    }

    private void showEmptyReviewsMessage() {
        rvReviews.setVisibility(View.INVISIBLE);
        emptyReviewMessage.setVisibility(View.VISIBLE);
    }

    private View initView(LayoutInflater inflater, ViewGroup container){
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        imageView = rootView.findViewById(R.id.iv_movie_backdrop);
        collapsingToolbarLayout = rootView.findViewById(R.id.collapsingTL);
        toolbar = rootView.findViewById(R.id.toolbar);
        ivMoviePoster = rootView.findViewById(R.id.iv_movie_poster);
        flowTextView = rootView.findViewById(R.id.ftv);
        tvOriginalTitle = rootView.findViewById(R.id.tv_original_title);
        tvRating = rootView.findViewById(R.id.tv_rating);
        tvReleaseDate = rootView.findViewById(R.id.tv_release_date);
        rvTrailer = rootView.findViewById(R.id.rv_trailers);
        rvReviews = rootView.findViewById(R.id.rv_reviews);
        emptyReviewMessage = rootView.findViewById(R.id.emptyReviewsMessage);
        emptyTrailersMessage = rootView.findViewById(R.id.emptyTrailersMessage);
        toolbar = rootView.findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.favorite);
        menu = toolbar.getMenu();

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                if(item.getItemId() == R.id.favorite){
                    if(item.getIcon().getConstantState().equals(getResources().getDrawable(R.drawable.ic_favorite).getConstantState())){
                        detailViewModel.deleteMovie(movie);
                        Toast.makeText(getContext(), "Movie removed ", Toast.LENGTH_LONG).show();

                    }else{
                        detailViewModel.insertMovie(movie);
                        Toast.makeText(getContext(), "Movie added", Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });
        return rootView;
    }

    /**
     * This method sets the detail view
     *
     * @param movie The selected selected movie from the RecyclerView
     */
    private void setDetailView(Movie movie) {
        Log.d(TAG, "Setting DetailView");
        if (movie.getPoster_path() != null) {
            String url = getResources().getString(R.string.poster_path) + movie.getPoster_path();
            Glide.with(getActivity()).load(url).into(ivMoviePoster);
            collapsingToolbarLayout.setTitle(movie.getTitle());
        }
        if (movie.getBackdrop_path() != null) {
            String url = getResources().getString(R.string.backdrop_path) + movie.getBackdrop_path();
            Glide.with(getActivity()).load(url).into(imageView);
            Spanned html = Html.fromHtml(movie.getOverview());
            flowTextView.setTextSize(65);
            flowTextView.setColor(R.color.colorContent);
            flowTextView.setText(html);
        }
        if (movie.getOriginal_title() != null) {
            tvOriginalTitle.setText(movie.getOriginal_title());

        }
        if (movie.getVote_average() > 0) {
            tvRating.setText(movie.getVote_average() + "/10");
        }
        if (movie.getRelease_date() != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);
                Date date = simpleDateFormat.parse(movie.getRelease_date());
                simpleDateFormat.applyPattern(NEW_FORMAT);
                tvReleaseDate.setText(simpleDateFormat.format(date).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}


