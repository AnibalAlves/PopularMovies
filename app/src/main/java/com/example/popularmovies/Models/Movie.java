package com.example.popularmovies.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movie {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    public int getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setId(int i)
    {
        this.id=i;
    }

    public void setTitle(String t){
        this.title=t;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}

