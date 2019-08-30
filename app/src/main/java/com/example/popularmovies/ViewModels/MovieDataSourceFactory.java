package com.example.popularmovies.ViewModels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.popularmovies.Models.TMDbAPI;

public class MovieDataSourceFactory extends MovieDataSource.Factory {

    private MovieDataSource movieDataSource;
    private TMDbAPI tmDbAPI;
    private MutableLiveData<MovieDataSource> mutableLiveData;

    public MovieDataSourceFactory(TMDbAPI tmDbAPI)
    {
        this.tmDbAPI = tmDbAPI;
        mutableLiveData=new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        movieDataSource = new MovieDataSource(tmDbAPI);
        Log.d("TESTTT",movieDataSource.toString());
        mutableLiveData.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableLiveData(){
        return mutableLiveData;
    }
}
