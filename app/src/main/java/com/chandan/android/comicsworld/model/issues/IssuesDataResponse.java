package com.chandan.android.comicsworld.model.issues;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IssuesDataResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private List<IssuesData> results;

    public IssuesDataResponse(List<IssuesData> results) {
        this.results = results;
    }

    public List<IssuesData> getResults() {
        return results;
    }

    public void setResults(List<IssuesData> results) {
        this.results = results;
    }
}
