package com.chandan.android.comicsworld.model.characters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterDetailDataResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private CharacterDetailData results;

    public CharacterDetailDataResponse(CharacterDetailData results) {
        this.results = results;
    }

    public CharacterDetailData getResults() {
        return results;
    }

    public void setResults(CharacterDetailData results) {
        this.results = results;
    }
}
