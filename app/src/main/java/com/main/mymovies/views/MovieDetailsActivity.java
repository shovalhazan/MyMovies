package com.main.mymovies.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.main.mymovies.Control.TMDB;
import com.main.mymovies.R;
import com.main.mymovies.model.MovieCallBack;
import com.main.mymovies.model.MyMovieModel;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE ="EXTRA_MOVIE";
    private  final String TAG ="MovieActivity" ;

    private MyMovieModel movieModel=null;
    private TextView LBL_title,LBL_genres,LBL_overview,LBL_languages,LBL_adult,LBL_runtime;

    private TMDB tmdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
            movieModel= (MyMovieModel)extras.getSerializable(EXTRA_MOVIE);
        Log.d(TAG, "onCreate: "+movieModel);
        tmdb= new TMDB(this);
        updateMovie();

    }

    private void updateMovie() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tmdb.getMovieDetails(new MovieCallBack(){
                    @Override
                    public void getUpdateMovie(MyMovieModel updateMovie) {
                        if(updateMovie!=null) {
                            movieModel = updateMovie;
                            findViews();
                            initViews();
                        }
                    }
                },movieModel);
            }
        });
        thread.start();
    }

    private void initViews() {
        String gen=movieModel.getGenre_ids()[0];
        String lan =movieModel.getSpoken_languages()[0];;
        for(int i=1; i < movieModel.getGenre_ids().length; i++)
            gen+=","+movieModel.getGenre_ids()[i];
        for(int i=1; i < movieModel.getSpoken_languages().length; i++)
            lan+=","+movieModel.getSpoken_languages()[i];

        LBL_title.setText(movieModel.getOriginal_title());
        LBL_runtime.setText("RUNTIME: "+movieModel.getRuntime());
        LBL_genres.setText(gen);
        LBL_overview.setText(movieModel.getOverview());
        LBL_adult.setText(movieModel.isAdult()?"Adult only":"For any age");
        LBL_languages.setText("original language: "+movieModel.getOriginal_language()+"\nspoken languages: "+lan);
    }

    private void findViews() {
         LBL_title=(TextView)findViewById(R.id.activity_movie_LBL_title);
         LBL_genres=(TextView)findViewById(R.id.activity_movie_LBL_genres);
         LBL_overview=(TextView)findViewById(R.id.activity_movie_LBL_overview);
         LBL_languages=(TextView)findViewById(R.id.activity_movie_LBL_languages);
         LBL_adult=(TextView)findViewById(R.id.activity_movie_LBL_adult);
         LBL_runtime=(TextView)findViewById(R.id.activity_movie_LBL_runtime);

    }
}