package com.main.mymovies.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.mymovies.model.MyMovieModel;
import com.main.mymovies.R;

import java.util.ArrayList;

public class MoviesListFragment extends Fragment{
    private static final String TAG = "RecyclerViewFragment";


    private RecyclerView mRecyclerView;
    private RecyclerViewMovieAdapter mAdapter;
    private ArrayList<MyMovieModel> mMoviesList;

    public MoviesListFragment(ArrayList<MyMovieModel> mMoviesList) {
        Log.d(TAG, "MoviesListFragment: "+mMoviesList);
        this.mMoviesList = mMoviesList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movies_fragment_LST_recyclerview);
        populateRecycleList();
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }


    private void populateRecycleList() {
        Log.d(TAG, "populateRecycleList: "+mMoviesList.size());
        if (mMoviesList != null) {
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter  = new RecyclerViewMovieAdapter(getContext(),mMoviesList);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Log.d(TAG, "populateEventList: No events to show");
            mRecyclerView.setVisibility(View.GONE);
        }
    }
}
