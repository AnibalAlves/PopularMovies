package com.example.popularmovies.ViewModels;

import android.app.Application;
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
        moviesList.setLayoutManager(new GridLayoutManager(this,3));
        moviesList.addItemDecoration(new DividerItemDecoration(moviesList.getContext(), DividerItemDecoration.VERTICAL));
        ll = findViewById(R.id.offLayout);
        if (mainActivityViewModel.isNetworkAvailable(this)) {
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
            }
        });
    }

    private void showOnRecyclerView(){
        MoviesAdapter moviesAdapter = new MoviesAdapter(this);
        moviesAdapter.submitList(movies);
        moviesList.setAdapter(moviesAdapter);
        moviesAdapter.notifyDataSetChanged();
    }

    //clicking refresh button after Internet Connection was lost
    public void refreshNet(View view) {
        Log.d("ClickedRe","Clicked refresh");
        ll.setVisibility(View.GONE);
        getMovies();
    }
}
