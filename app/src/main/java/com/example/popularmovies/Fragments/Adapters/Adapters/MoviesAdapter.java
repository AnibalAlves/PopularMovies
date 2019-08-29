package com.example.popularmovies.Fragments.Adapters.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.R;
import java.util.List;


public class MoviesAdapter extends PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder> {

    Context context;
    //private List<Movie> movies;
    private String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public MoviesAdapter(Context context)
    {
        super(Movie.CALLBACK);
        this.context=context;
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

    /*@Override
    public int getItemCount() {
        return movies.size();
    }*/

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
