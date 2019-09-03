package com.example.popularmovies.View;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.Models.MovieDataSourceFactory;
import com.example.popularmovies.Models.TMDbAPI;
import com.example.popularmovies.Utils.TMDbAPIInstance;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivityViewModel extends AndroidViewModel {
    private Executor executor;
    private LiveData<PagedList<Movie>> moviesPagedList;
    private static MainActivityViewModel repository;
    TMDbAPI tmDbAPI;
    private static Context context;
    private static int pageSize = 20;
    private static int distanceToLoadNextPage=11;
    private static int initialLoadSize=21; //typically this size is larger than the page size, and indicates the number of items to load initially

    public MainActivityViewModel(@NonNull Application application, Context context)
    {
        super(application);
        this.context=context;
        tmDbAPI = TMDbAPIInstance.getInstance();
        MovieDataSourceFactory factory = new MovieDataSourceFactory(context);

        PagedList.Config config = (new PagedList.Config.Builder())
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(initialLoadSize)
                .setPageSize(pageSize) //9 movies per page
                .setPrefetchDistance(distanceToLoadNextPage) //after 9th movie, fetch another 15
                .build();

        executor = Executors.newFixedThreadPool(1);

        //
        moviesPagedList = (new LivePagedListBuilder<Long, Movie>(factory,config))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<Movie>> getMoviesPagedList(){
        return moviesPagedList;
    }

}
