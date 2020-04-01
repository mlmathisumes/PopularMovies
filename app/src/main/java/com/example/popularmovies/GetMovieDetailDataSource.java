package com.example.popularmovies;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies.model.Review;
import com.example.popularmovies.model.ReviewResultsList;
import com.example.popularmovies.model.Trailer;
import com.example.popularmovies.model.TrailerResultsList;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMovieDetailDataSource {

    private static final String TAG = GetMovieDetailDataSource.class.getSimpleName();
    private final HashMap<String, MutableLiveData<List<Review>>> reviewCache;
    private final HashMap<String, MutableLiveData<List<Trailer>>> trailerCache;

    private static GetMovieDetailDataSource sInstance;
    private final  GetMoviesDataService getMoviesDataService;
    private final static Object LOCK = new Object();
    private static final String API_KEY = "f4ea32f9b797782899f3f11f86e81007";

    private GetMovieDetailDataSource(GetMoviesDataService getMoviesDataService){
        this.getMoviesDataService = getMoviesDataService;
        reviewCache = new HashMap<>();
        trailerCache = new HashMap<>();
    }

    public synchronized static GetMovieDetailDataSource getInstance(GetMoviesDataService getMoviesDataService){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new GetMovieDetailDataSource(getMoviesDataService);
                Log.d(TAG,"Creating GetMovieDetailDataSource instance");
            }
        }
        return sInstance;
    }

    /**
     * This method fetches the reviews from the cache data
     *
     * @param Id The selected movies id value
     */
    public MutableLiveData<List<Review>> getReview(int Id){
        MutableLiveData<List<Review>> reviewList = reviewCache.get(Id);
        if(reviewList == null){
            MutableLiveData newReviewList = new MutableLiveData();
            fetchReviews(Id, newReviewList);
            reviewList = newReviewList;
        }
        return reviewList;
    }

    /**
     * This method adds the selected movie to your favorite list
     *
     * @param Id The selected movies id value
     * @param reviewLiveData The MutableLiveData listing of reviews which is being set from a Retrofit call
     */
    private void fetchReviews(int Id, final MutableLiveData<List<Review>> reviewLiveData){
        Log.d(TAG, "Fetching review data");
        try{
            Call<ReviewResultsList> call = getMoviesDataService.getReviews(Id, API_KEY);
            call.enqueue(new Callback<ReviewResultsList>() {
                @Override
                public void onResponse(Call<ReviewResultsList> call, Response<ReviewResultsList> response) {
                    List<Review> reviews = response.body().getReviewList();
                    if(reviews != null){
                        reviewLiveData.setValue(reviews);
                        Log.d(TAG, "Reviews Live Data set");
                    }else {
                        reviewLiveData.setValue(null);
                        Log.d(TAG, "No Reviews ");
                    }
                }

                @Override
                public void onFailure(Call<ReviewResultsList> call, Throwable t) {
                    Log.d(TAG + "Review", "Something went wrong.. Error message: " + t.getMessage());


                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * This method fetches the trailers from the cache data
     *
     * @param id The selected movies id value
     */
   public MutableLiveData<List<Trailer>> getTrailers(int id){
       MutableLiveData<List<Trailer>> trailerList = trailerCache.get(id);
       if(trailerList == null){
           MutableLiveData newTrailerList = new MutableLiveData();
           fetchTrailers(id, newTrailerList);
           trailerList = newTrailerList;
       }
       return trailerList;
    }

    /**
     * This method adds the selected movie to your favorite list
     *
     * @param Id The selected movies id value
     * @param trailerLiveData The MutableLiveData listing of trailers which is being set from a Retrofit call
     */
    private void fetchTrailers(int Id, final MutableLiveData<List<Trailer>> trailerLiveData) {
        Log.d(TAG, "Fetching trailer data");
        try{
            Call<TrailerResultsList> call = getMoviesDataService.getTrailers(Id, API_KEY);
            call.enqueue(new Callback<TrailerResultsList>() {
                @Override
                public void onResponse(Call<TrailerResultsList> call, Response<TrailerResultsList> response) {
                    List<Trailer> trailers = response.body().getTrailerList();
                    if(trailers != null){
                        trailerLiveData.setValue(trailers);
                        Log.d(TAG, "Trailer Live Data set");
                    }else {
                        trailerLiveData.setValue(null);
                        Log.d(TAG, "No Trailers ");
                    }
                }

                @Override
                public void onFailure(Call<TrailerResultsList> call, Throwable t) {
                    Log.d(TAG + " Trailer", "Something went wrong.. Error message: " + t.getMessage());

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
