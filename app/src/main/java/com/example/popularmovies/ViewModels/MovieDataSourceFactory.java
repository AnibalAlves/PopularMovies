package com.example.popularmovies.ViewModels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.popularmovies.Models.TMDbAPI;


/*
Class responsible for creating MovieDataSource for fetching new Movie pages
 */
public class MovieDataSourceFactory extends MovieDataSource.Factory {

    private MovieDataSource movieDataSource;
    private TMDbAPI tmDbAPI;
    private MutableLiveData<MovieDataSource> mutableLiveData;
    private static Context context;

    public MovieDataSourceFactory(TMDbAPI tmDbAPI, Context context)
    {
        this.tmDbAPI = tmDbAPI;
        this.context=context;
        mutableLiveData=new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        movieDataSource = new MovieDataSource(tmDbAPI,context);
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableLiveData(){
        return mutableLiveData;
    }
}
