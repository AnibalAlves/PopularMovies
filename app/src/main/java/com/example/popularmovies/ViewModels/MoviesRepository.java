package com.example.popularmovies.ViewModels;

import android.util.Log;

import com.example.popularmovies.Models.MovieHandler;
import com.example.popularmovies.Models.OnGetMoviesCallback;
import com.example.popularmovies.Models.TMDbAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";

    //change to your TMDbAPI key
    private static final String API_KEY = "";

    private static MoviesRepository repository;
    private TMDbAPI api;

    private MoviesRepository(TMDbAPI a)
    {
        this.api=a;
    }

    //Singleton pattern in order to allow just 1 instance of repository class
    public static MoviesRepository getInstance()
    {
        if (repository==null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            repository=new MoviesRepository(retrofit.create(TMDbAPI.class));
        }
        return repository;
    }

    public void getMovies(final OnGetMoviesCallback callback)
    {
        //change the page number to correspond to the requested movies loaded per page
        api.getPopularMovies(API_KEY, LANGUAGE, 2).enqueue(new Callback<MovieHandler>() {
            @Override
            public void onResponse(Call<MovieHandler> call, Response<MovieHandler> response) {
                if (response.isSuccessful())
                {
                    MovieHandler movieResponse = response.body();
                    Log.d("")
                    if (movieResponse!=null && movieResponse.getMovies()!=null)
                        callback.onSuccess(movieResponse.getMovies());
                    else{
                        callback.onError();
                    }
                }
                else
                    callback.onError();
            }

            @Override
            public void onFailure(Call<MovieHandler> call, Throwable t) {
                callback.onError();
            }
        });
    }
}
