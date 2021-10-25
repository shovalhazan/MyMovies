package com.main.mymovies.Control;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.main.mymovies.model.MoeviesListCallBack;
import com.main.mymovies.model.QueueSingleton;
import com.main.mymovies.R;
import com.main.mymovies.model.MovieCallBack;
import com.main.mymovies.model.MyMovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TMDB {
    private static final String TAG = "TMDB";
    private final int NUMBER_OF_MOVIES_TO_SHOW=5;

    private Context context;
    private MyMovieModel movieModel;

    private String apiKey;
    private String base_url = "https://api.themoviedb.org/3/movie/";
    private String get_popular_movies_url;
    private String get_movie_details_url;

    private ArrayList<MyMovieModel> mMoviesList;

    public TMDB(Context context) {
        Log.d(TAG, "TMDB: ");
        this.context = context;
        mMoviesList= new ArrayList<MyMovieModel>();
        apiKey = context.getResources().getString(R.string.movies_api_key);
    }

    /**
     * Use this to get details abut movie using api
     *
     * @param callBack for the update movie
     * @param movieModel the original movie with the specific ID
     *
     */
    public void getMovieDetails( MovieCallBack callBack, MyMovieModel movieModel) {
        Log.d(TAG, "getMovieDetails: ");
        this.movieModel = movieModel;
        get_movie_details_url = base_url + movieModel.getId() + "?api_key=" + apiKey + "&language=en-US";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, get_movie_details_url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: " + response);
                        createMovie(response);
                        Log.d(TAG, "onResponse: "+movieModel.toString());
                        callBack.getUpdateMovie(movieModel);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: ");
                        // TODO: Handle error
                        error.printStackTrace();

                    }
                });
        QueueSingleton.getInstance().addToRequestQueue(jsonObjectRequest);

    }
    /**
     * create a movie model from jsonObject
     *
     * @param response - the Json with the details
     */
    private void createMovie(JSONObject response) {
        try {
            movieModel.setAdult(response.getBoolean("adult"));
            movieModel.setOverview(response.getString("overview"));
            movieModel.setGenre_ids(convertJsonArrToStringArr(response.getJSONArray("genres"),"name"));
            movieModel.setOriginal_language(response.getString("original_language"));
            movieModel.setBackdrop_path("backdrop_path");
            movieModel.setSpoken_languages(convertJsonArrToStringArr(response.getJSONArray("spoken_languages"),"english_name"));
            movieModel.setRuntime(response.getInt("runtime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Use this to get list of popular movie using api
     *
     * @param callBack for the top popular list
     */
    public void getPopularMovies(MoeviesListCallBack callBack) {
        get_popular_movies_url = base_url + "popular?api_key=" + apiKey + "&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, get_popular_movies_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: "+response);
                        JSONArray results = null;
                        try {
                            results = response.getJSONArray("results");
                            Log.d(TAG, "onResponse: "+results.length());
                            poplateMoviesList(results);
                            callBack.getMoviesList(mMoviesList);
                        } catch (JSONException e) {
                            Log.d(TAG, "onResponse: EXCEPTION");
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();

                    }
                });

// Access the RequestQueue through your singleton class.
        QueueSingleton.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void poplateMoviesList(JSONArray results){
        for (int i = 0; i < NUMBER_OF_MOVIES_TO_SHOW; i++) {
            JSONObject movie = null;
            try {
                movie = results.getJSONObject(i);
                long id = movie.optLong("id");
                String title = movie.optString("original_title");
                JSONArray genres =movie.optJSONArray("genre_ids");
                String [] genresArr = populateGeneresArray(genres);
                Log.d(TAG, "onResponse: "+id+" title "+title+" genres "+genres.length());
                mMoviesList.add(new MyMovieModel(genresArr,title,id));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    private  String [] populateGeneresArray(JSONArray genres){
        String [] genresArr = new String[genres.length()];
        for (int j=0;j<genres.length();j++){
            try {
                genresArr[j]=genres.getString(j);
                Log.d(TAG, "onResponse: "+genres.getString(j));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return genresArr;
    }

    /**
     * Use this to convert JsonArray TO String []
     *
     * @param genres JsonArray to convert
     * @param name - the name of attribute that we want in the array
     * @return the converting array
     */
    private String[] convertJsonArrToStringArr(JSONArray genres,String name) {
        String[] stringArr= new String[genres.length()];
        for(int i=0;i<stringArr.length;i++) {
            try {
                JSONObject gen= genres.getJSONObject(i);
                stringArr[i]=gen.getString(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stringArr;
    }


}
