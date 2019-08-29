package com.example.popularmovies.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.Models.TMDbAPI;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityViewModel extends AndroidViewModel {
    private Executor executor;
    private LiveData<PagedList<Movie>> moviesPagedList;
    private static MainActivityViewModel repository;
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = "";
    TMDbAPI tmDbAPI;

    //Singleton pattern in order to allow just 1 instance of repository class
    public static TMDbAPI getInstance()
    {
        if (repository==null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            //repository=new MainActivityViewModel(retrofit.create(TMDbAPI.class));
            return retrofit.create(TMDbAPI.class);
        }
        else
            return repository.tmDbAPI;
    }

    public MainActivityViewModel(@NonNull Application application)
    {
        super(application);
        tmDbAPI = getInstance();
        MovieDataSourceFactory factory = new MovieDataSourceFactory(tmDbAPI);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(9)
                .setPageSize(9)
                .setPrefetchDistance(9)
                .build();

        executor = Executors.newFixedThreadPool(5);

        moviesPagedList = (new LivePagedListBuilder<Long, Movie>(factory,config))
                .setFetchExecutor(executor)
                .build();
        Log.d("TESTTT",String.valueOf(moviesPagedList.hasObservers()));
    }

    public LiveData<PagedList<Movie>> getMoviesPagedList(){
        return moviesPagedList;
    }
}
