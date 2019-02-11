package com.chandan.android.comicsworld.model.movies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesDataResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private List<MoviesData> results;

    public MoviesDataResponse(List<MoviesData> results) {
        this.results = results;
    }

    public List<MoviesData> getResults() {
        return results;
    }

    public void setResults(List<MoviesData> results) {
        this.results = results;
    }
}
