package com.chandan.android.comicsworld.model.issues;

import com.google.gson.annotations.SerializedName;

public class IssueDetailDataResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private IssueDetailData results;

    public IssueDetailDataResponse(IssueDetailData results) {
        this.results = results;
    }

    public IssueDetailData getResults() {
        return results;
    }

    public void setResults(IssueDetailData results) {
        this.results = results;
    }
}
