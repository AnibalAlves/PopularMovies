package com.example.popularmovies.Models;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.popularmovies.ViewModels.MovieDataSource;


/*
Class responsible for creating MovieDataSource for fetching new Movie pages
 */
public class MovieDataSourceFactory extends DataSource.Factory {

    private MovieDataSource movieDataSource;
    private MutableLiveData<MovieDataSource> mutableLiveData;
    private static Context context;

    public MovieDataSourceFactory(Context context)
    {
        this.context=context;
        mutableLiveData=new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        movieDataSource = new MovieDataSource(context);
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableLiveData(){
        return mutableLiveData;
    }
}
