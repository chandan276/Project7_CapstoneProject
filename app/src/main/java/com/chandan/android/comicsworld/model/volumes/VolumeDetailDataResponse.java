package com.chandan.android.comicsworld.model.volumes;

import com.google.gson.annotations.SerializedName;

public class VolumeDetailDataResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private VolumeDetailData results;

    public VolumeDetailDataResponse(VolumeDetailData results) {
        this.results = results;
    }

    public VolumeDetailData getResults() {
        return results;
    }

    public void setResults(VolumeDetailData results) {
        this.results = results;
    }
}
