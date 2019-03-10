package com.chandan.android.comicsworld.model.movies;

import com.google.gson.annotations.SerializedName;

public class MovieDetailDataResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private MovieDetailData results;

    public MovieDetailDataResponse(MovieDetailData results) {
        this.results = results;
    }

    public MovieDetailData getResults() {
        return results;
    }

    public void setResults(MovieDetailData results) {
        this.results = results;
    }
}
