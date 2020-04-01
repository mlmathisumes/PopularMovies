package com.example.popularmovies.utils;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Utility function to use Retrofit for networking
 */
class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";


    /*
    * Create an instance of Retrofit object
     */
    public static  Retrofit getRetrofitInstance(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
