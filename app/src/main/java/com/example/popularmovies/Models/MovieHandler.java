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

    public void setPage(int page){
        this.page=page;
    }
    public void setTotalResults(int totalResults){
        this.totalResults=totalResults;
    }
    public void setMovies(List<Movie> movies)
    {
        this.movies=movies;
    }
    public void setTotalPages(int totalPages){
        this.totalPages=totalPages;
    }

}
