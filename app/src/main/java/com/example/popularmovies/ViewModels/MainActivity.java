package com.example.popularmovies.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.popularmovies.Fragments.Adapters.Adapters.MoviesAdapter;
import com.example.popularmovies.Fragments.Adapters.Fragment.OffLineFragment;
import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.Models.OnGetMoviesCallback;
import com.example.popularmovies.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesList;
    private MoviesAdapter adapter;
    private int lastVisibleRecyclerPosition=0;

    private MoviesRepository moviesRepository;
    private PagedList<Movie> movies;
    private MainActivityViewModel mainActivityViewModel;
    final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    private boolean loading=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        moviesList = findViewById(R.id.moviesList);
        if (isNetworkAvailable()) {
            getMovies();
            /*moviesRepository = MoviesRepository.getInstance();

            moviesList = findViewById(R.id.moviesList);

            moviesList.setLayoutManager(layoutManager);
            //fill the repository with a list of movies
            moviesRepository.getMovies(new OnGetMoviesCallback() {
                @Override
                public void onSuccess(List<Movie> movies) {
                    adapter = new MoviesAdapter(movies);
                    moviesList.setAdapter(adapter);
                    moviesList.addItemDecoration(new DividerItemDecoration(moviesList.getContext(), DividerItemDecoration.VERTICAL));
                    moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        /*@Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);

                            /*if (!recyclerView.canScrollVertically(1)) {
                                Toast.makeText(MainActivity.this, "Last", Toast.LENGTH_LONG).show();

                            }
                        }*/

                        /*@Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            /*lastVisibleRecyclerPosition = layoutManager.findLastVisibleItemPosition();
                            Log.d("STATEOFSCROLL",String.valueOf(lastVisibleRecyclerPosition));
                            if (lastVisibleRecyclerPosition==9)
                            {
                                Toast.makeText(MainActivity.this, "Nine position reached", Toast.LENGTH_LONG).show();
                            }
                            //Log.d("STATEOFSCROLL",String.valueOf(dy));
                            if (dy > 0) {
                                // Scrolling up
                            } else {
                                // Scrolling down
                            }
                            int visibleItemCount = layoutManager.getChildCount();
                            int totalItemCount = layoutManager.getItemCount();
                            int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                            if (loading) {
                                if ((visibleItemCount + pastVisibleItems) >= 9)
                                {
                                    loading = false;
                                    Log.e("CHECKPOS", "Last Item Wow !");
                                    loading = true;
                                    //Do pagination.. i.e. fetch new data
                                }
                            }
                        }

                    });
                }

                @Override
                public void onError() {
                    Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            });*/
        }
        else {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fg1,new OffLineFragment(),"");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    //check for Internet connectivity
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void getMovies()
    {
        mainActivityViewModel.getMoviesPagedList().observe(this, new Observer<PagedList<Movie>>(){

            @Override
            public void onChanged(PagedList<Movie> moviesFromLiveData) {
                movies = moviesFromLiveData;
                Log.d("TESTTT",String.valueOf(movies.size()));
                showOnRecyclerView();
            }
        });
    }

    private void showOnRecyclerView(){
        //moviesList = recyclerView
        Log.d("MOVIE_SIZE",String.valueOf(movies.size()));
        MoviesAdapter moviesAdapter = new MoviesAdapter(this);
        moviesAdapter.submitList(movies);
        moviesList.setLayoutManager(new LinearLayoutManager(this));
        moviesList.addItemDecoration(new DividerItemDecoration(moviesList.getContext(), DividerItemDecoration.VERTICAL));
        moviesList.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }
}
