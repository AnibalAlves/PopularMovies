package com.example.popularmovies.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieHandler {

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    @SerializedName("results")
    @Expose
    private List<Movie> movies;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    public int getPage(){
        return this.page;
    }

    public int getTotalResults(){
        return this.totalResults;
    }

    public List<Movie> getMovies(){
        return this.movies;
    }

    public int getTotalPages(){
        return this.totalPages;
    }

    public void setPage(int p){
        this.page=p;
    }
    public void setTotalResults(int tr){
        this.totalResults=tr;
    }
    public void setMovies(List<Movie> mvs)
    {
        this.movies=mvs;
    }
    public void setTotalPages(int tp){
        this.totalPages=tp;
    }

}
