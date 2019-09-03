package com.example.popularmovies.Fragments.Adapters.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.R;


public class MoviesAdapter extends PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder> {

    //private List<Movie> movies;
    private static int widthSize=400, heightSize=400;

    public MoviesAdapter()
    {
        super(Movie.CALLBACK);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_row, parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position)
    {
        //Commented... Original has 2 lines of code here. If implemented, must change movie model class
        Movie movie = getItem(position);
        holder.bind(movie);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder
    {
        ImageButton poster;
        TextView title;

        public MovieViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.moviePoster);
            title = itemView.findViewById(R.id.movieName);
        }

        public void bind(Movie movie)
        {
            title.setText(movie.getTitle());
            if (movie.getPosterPath()!=null) {
                Glide.with(itemView)
                        .load(movie.getIMAGE_BASE_URL() + movie.getPosterPath())
                        .apply(new RequestOptions().override(widthSize, heightSize))
                        .into(poster);
            }
            else
            {
                //Do something like putting a placeholder
                Glide.with(itemView)
                        .load(R.drawable.transferir)
                        .apply(new RequestOptions().override(widthSize, heightSize))
                        .into(poster);
            }
        }
    }
}
