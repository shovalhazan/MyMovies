package com.main.mymovies.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.mymovies.Control.TMDB;
import com.main.mymovies.model.MoeviesListCallBack;
import com.main.mymovies.model.MyMovieModel;
import com.main.mymovies.model.QueueSingleton;
import com.main.mymovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MoviesListFragment moviesListFragment;
    private ArrayList<MyMovieModel> mMoviesList;
    private TMDB tmdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tmdb= new TMDB(this);
        tmdb.getPopularMovies(new MoeviesListCallBack(){
            @Override
            public void getMoviesList(ArrayList<MyMovieModel> list) {
                mMoviesList =list;
                openMoviesFragment();
            }
        });
    }
    private void openMoviesFragment(){
        Log.d(TAG, "initMoviesFragment: "+mMoviesList.size());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        moviesListFragment=new MoviesListFragment(mMoviesList);
        ft.add(R.id.placeHolder, moviesListFragment).commit();
    }


}