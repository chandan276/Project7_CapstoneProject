package com.chandan.android.comicsworld.model.volumes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolumesDataResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private List<VolumesData> results;

    public VolumesDataResponse(List<VolumesData> results) {
        this.results = results;
    }

    public List<VolumesData> getResults() {
        return results;
    }

    public void setResults(List<VolumesData> results) {
        this.results = results;
    }
}
