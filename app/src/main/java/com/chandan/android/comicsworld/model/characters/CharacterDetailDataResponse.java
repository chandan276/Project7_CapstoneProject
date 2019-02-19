package com.chandan.android.comicsworld.model.characters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterDetailDataResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private List<CharacterDetailData> results;

    public CharacterDetailDataResponse(List<CharacterDetailData> results) {
        this.results = results;
    }

    public List<CharacterDetailData> getResults() {
        return results;
    }

    public void setResults(List<CharacterDetailData> results) {
        this.results = results;
    }
}
