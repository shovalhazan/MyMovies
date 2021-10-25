package com.main.mymovies.model;

import java.io.Serializable;

public class MyMovieModel implements Serializable {

    private String backdrop_path;
    private String[] genre_ids;
    private String[] spoken_languages;
    private String original_language;
    private String original_title;
    private String overview;
    private long id;
    private boolean adult;
    private int runtime;


    public MyMovieModel() {
    }

    public MyMovieModel(String[] genre_ids, String original_titel, long id) {
        this.genre_ids = genre_ids;
        this.original_title = original_titel;
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String[] getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(String[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public String[] getSpoken_languages() {
        return spoken_languages;
    }

    public void setSpoken_languages(String[] spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }
}
