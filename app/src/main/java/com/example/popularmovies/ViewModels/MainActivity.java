package com.example.popularmovies.ViewModels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.popularmovies.Fragments.MoviesAdapter;
import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.Models.OnGetMoviesCallback;
import com.example.popularmovies.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesList;
    private MoviesAdapter adapter;

    private MoviesRepository moviesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isNetworkAvailable()) {
            moviesRepository = MoviesRepository.getInstance();

            moviesList = findViewById(R.id.moviesList);
            moviesList.setLayoutManager(new LinearLayoutManager(this));

            moviesRepository.getMovies(new OnGetMoviesCallback() {
                @Override
                public void onSuccess(List<Movie> movies) {
                    adapter = new MoviesAdapter(movies);
                    moviesList.setAdapter(adapter);
                }

                @Override
                public void onError() {
                    Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(this,"No internet!",Toast.LENGTH_LONG).show();
    }

    //check for Internet connectivity
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
