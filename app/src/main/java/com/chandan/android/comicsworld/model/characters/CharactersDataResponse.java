package com.chandan.android.comicsworld.model.characters;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharactersDataResponse {
    private final static String Results_Tag = "results";

    @SerializedName(Results_Tag)
    private List<CharactersData> results;

    public CharactersDataResponse(List<CharactersData> results) {
        this.results = results;
    }

    public List<CharactersData> getResults() {
        return results;
    }

    public void setResults(List<CharactersData> results) {
        this.results = results;
    }
}
