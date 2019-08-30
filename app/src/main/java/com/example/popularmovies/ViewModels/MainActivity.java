package com.example.popularmovies.ViewModels;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.Fragments.Adapters.Adapters.MoviesAdapter;
import com.example.popularmovies.Fragments.Adapters.Fragment.OffLineFragment;
import com.example.popularmovies.Models.Movie;
import com.example.popularmovies.Models.TMDbAPI;
import com.example.popularmovies.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesList;
    private PagedList<Movie> movies;
    private MainActivityViewModel mainActivityViewModel;
    private MovieDataSource movieDataSource;
    LinearLayout ll;
    Application app;
    private TMDbAPI tmDbAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = new Application();
        mainActivityViewModel = new MainActivityViewModel(app,this);
        movieDataSource = new MovieDataSource(tmDbAPI,this);
        moviesList = findViewById(R.id.moviesList);
        ll = findViewById(R.id.offLayout);
        if (isNetworkAvailable()) {
            getMovies();
        }
        else {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragContainer,new OffLineFragment(),"");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    //check for Internet connectivity
    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
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

        movieDataSource.getInternetConnection().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d("StateOfView",String.valueOf(aBoolean));
                if (!aBoolean)
                    ll.setVisibility(View.VISIBLE);
                else
                    ll.setVisibility(View.GONE);
            }
        });
    }

    public void showOffView()
    {
        LinearLayout offLayout = findViewById(R.id.offLayout);
        offLayout.setVisibility(View.VISIBLE);
    }

    private void showOnRecyclerView(){
        //moviesList = recyclerView
        //Log.d("MOVIE_SIZE",String.valueOf(movies.size()));
        MoviesAdapter moviesAdapter = new MoviesAdapter(this);
        moviesAdapter.submitList(movies);
        moviesList.setLayoutManager(new GridLayoutManager(this,3));
        moviesList.addItemDecoration(new DividerItemDecoration(moviesList.getContext(), DividerItemDecoration.VERTICAL));
        moviesList.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }

    public void refreshNet(View view) {
        
    }
}
