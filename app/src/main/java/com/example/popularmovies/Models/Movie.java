package com.example.popularmovies.Models;

import androidx.recyclerview.widget.DiffUtil;

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

    public void setId(int id)
    {
        this.id=id;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public static final DiffUtil.ItemCallback<Movie> CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
            return true;
        }
    };
}

