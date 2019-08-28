package com.example.popularmovies.Fragments.Adapters.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.R;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{

    private List<Movie> movies;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public MoviesAdapter(List<Movie> li)
    {
        this.movies=li;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_row, parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position)
    {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder
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
            //Log.d("IMAGE URL", movie.getPosterPath());

            //changing all images path to null
            //uncomment to test image not fetched
            //movie.setPosterPath(null);

            if (movie.getPosterPath()!=null) {
                Glide.with(itemView)
                        .load(IMAGE_BASE_URL + movie.getPosterPath())
                        .apply(new RequestOptions().override(500, 500))
                        .into(poster);
            }
            else
            {
                //Do something like putting a placeholder
                Glide.with(itemView)
                        .load(R.drawable.transferir)
                        .apply(new RequestOptions().override(500, 500))
                        .into(poster);
            }
        }
    }
}
