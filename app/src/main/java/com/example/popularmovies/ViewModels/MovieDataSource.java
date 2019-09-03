package com.example.popularmovies.ViewModels;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.Models.MovieHandler;
import com.example.popularmovies.Models.TMDbAPI;
import com.example.popularmovies.Utils.NetworkAvailability;
import com.example.popularmovies.Utils.TMDbAPIInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {

    private TMDbAPI tmDbAPI;
    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = "";
    private static Context context;
    private static MutableLiveData<Boolean> liveData = new MutableLiveData<>();

    public MovieDataSource(Context context)
    {
        this.context=context;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, Movie> callback) {
        tmDbAPI = TMDbAPIInstance.getInstance();
        //change the page number to correspond to the requested movies loaded per page
        tmDbAPI.getPopularMovies(API_KEY, LANGUAGE, 1).enqueue(new Callback<MovieHandler>() {
            @Override
            public void onResponse(@NonNull Call<MovieHandler> call,@NonNull Response<MovieHandler> response) {
                if (response.isSuccessful())
                {
                    MovieHandler movieResponse = response.body();
                    List<Movie> movies;
                    if (movieResponse!=null && movieResponse.getMovies()!=null) {
                        movies = movieResponse.getMovies();
                        callback.onResult(movies,null, (long) 1);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieHandler> call,@NonNull Throwable t) {
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, Movie> callback) {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, Movie> callback) {
        tmDbAPI = TMDbAPIInstance.getInstance();
        //check here for Internet Connection
        if (NetworkAvailability.isConnectedToNetwork(context)) {
            liveData.postValue(true);
            tmDbAPI.getPopularMovies(API_KEY, LANGUAGE, Math.toIntExact(params.key)+1).enqueue(new Callback<MovieHandler>() {
                @Override
                public void onResponse(@NonNull Call<MovieHandler> call,@NonNull Response<MovieHandler> response) {
                    if (response.isSuccessful()) {
                        MovieHandler movieResponse = response.body();
                        List<Movie> movies;
                        if (movieResponse != null && movieResponse.getMovies() != null) {
                            movies = movieResponse.getMovies();
                            callback.onResult(movies, params.key + 1);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieHandler> call,@NonNull Throwable t) {
                }
            });
        }
        else{ //NO internet connection
            liveData.postValue(false);
        }
    }

    //return boolean to observers
    public LiveData<Boolean> getInternetConnection()
    {
        return liveData;
    }
}
