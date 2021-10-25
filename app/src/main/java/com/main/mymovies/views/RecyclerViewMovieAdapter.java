package com.main.mymovies.views;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.mymovies.model.MyMovieModel;
import com.main.mymovies.R;

import java.util.ArrayList;

import static com.main.mymovies.views.MovieDetailsActivity.EXTRA_MOVIE;

public class RecyclerViewMovieAdapter  extends RecyclerView.Adapter<RecyclerViewMovieAdapter.ViewHolder> {

    private  final String TAG ="RecyclerViewAdapter" ;
    private ArrayList<MyMovieModel> topMoviesList;
    private Context context ;


    public RecyclerViewMovieAdapter(Context context, ArrayList<MyMovieModel> dataSet) {
        Log.d(TAG, "RecyclerViewMovieAdapter: "+dataSet.toString()+" size "+dataSet.size());
        topMoviesList = dataSet;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerViewMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_row_adapter, parent, false);
        return new RecyclerViewMovieAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMovieAdapter.ViewHolder holder, int position) {
        MyMovieModel temp = topMoviesList.get(position);
        String name = temp.getOriginal_title();

        holder.LBL_movie_name.setText(name);
        holder.LBL_movie_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                openMovieActivity(v.getContext(),temp);
            }
        });


    }

    @Override
    public int getItemCount() {
        return topMoviesList.size();
    }

    private void openMovieActivity(Context context,MyMovieModel movie){
        Intent myIntent = new Intent(context, MovieDetailsActivity.class);
        myIntent.putExtra(EXTRA_MOVIE,movie);
        context.startActivity(myIntent);
    }

    /**
     * An inner class to specify each row contents
     */
    public class ViewHolder extends RecyclerView.ViewHolder { // To hold each row

        TextView LBL_movie_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }
        private void initViews(View itemView) {
            LBL_movie_name=(TextView) itemView.findViewById(R.id.movie_row_LBL_movie_name);
        }
    }

}