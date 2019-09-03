package com.example.popularmovies.Utils;

import com.example.popularmovies.Models.TMDbAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMDbAPIInstance {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static TMDbAPI tmDbAPI;

    //Singleton pattern in order to allow just 1 instance of repository class
    public static TMDbAPI getInstance()
    {
        if (tmDbAPI==null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(TMDbAPI.class);
        }
        else
            return tmDbAPI;
    }
}
