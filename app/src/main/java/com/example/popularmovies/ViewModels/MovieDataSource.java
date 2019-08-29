package com.example.popularmovies.ViewModels;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.paging.PageKeyedDataSource;

import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.Models.MovieHandler;
import com.example.popularmovies.Models.TMDbAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {

    private TMDbAPI tmDbAPI;
    private static MovieDataSource repository;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = "386ffb49244f44ff9cc21b6ea8f02190";

    public MovieDataSource(TMDbAPI tmDbAPI)
    {
        this.tmDbAPI=tmDbAPI;
    }

    //Singleton pattern in order to allow just 1 instance of repository class
    public static TMDbAPI getInstance()
    {
        if (repository==null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            repository=new MovieDataSource(retrofit.create(TMDbAPI.class));
            return retrofit.create(TMDbAPI.class);
        }
        else
            return repository.tmDbAPI;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Movie> callback) {
        tmDbAPI = getInstance();
        //change the page number to correspond to the requested movies loaded per page
        tmDbAPI.getPopularMovies(API_KEY, LANGUAGE, 1).enqueue(new Callback<MovieHandler>() {
            @Override
            public void onResponse(Call<MovieHandler> call, Response<MovieHandler> response) {
                if (response.isSuccessful())
                {
                    MovieHandler movieResponse = response.body();
                    List<Movie> movies;
                    //Log.d("bodyResponse",movieResponse.getMovies().toString());
                    if (movieResponse!=null && movieResponse.getMovies()!=null) {
                        //callback.onSuccess(movieResponse.getMovies());
                        movies = movieResponse.getMovies();
                        Log.d("TESTTT","Movies fetched inside MovieDataSource " + String.valueOf(movies.size()));
                        callback.onResult(movies,null, (long) 2);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieHandler> call, Throwable t) {
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movie> callback) {
        tmDbAPI = getInstance();
        tmDbAPI.getPopularMovies(API_KEY, LANGUAGE, Math.toIntExact(params.key)).enqueue(new Callback<MovieHandler>() {
            @Override
            public void onResponse(Call<MovieHandler> call, Response<MovieHandler> response) {
                if (response.isSuccessful())
                {
                    MovieHandler movieResponse = response.body();
                    List<Movie> movies;
                    //Log.d("bodyResponse",movieResponse.getMovies().toString());
                    if (movieResponse!=null && movieResponse.getMovies()!=null) {
                        //callback.onSuccess(movieResponse.getMovies());
                        movies = movieResponse.getMovies();
                        callback.onResult(movies,params.key + 1);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieHandler> call, Throwable t) {
            }
        });
    }
}