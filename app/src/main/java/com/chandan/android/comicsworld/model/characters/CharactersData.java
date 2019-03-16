package com.chandan.android.comicsworld.model.characters;

import android.os.Parcel;
import android.os.Parcelable;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.google.gson.annotations.SerializedName;

public class CharactersData {

    private final static String Id_Tag = "id";
    private final static String Name_Tag = "name";
    private final static String Image_Data_Tag = "image";

    @SerializedName(Id_Tag)
    private Integer characterId;

    @SerializedName(Name_Tag)
    private String characterName;

    @SerializedName(Image_Data_Tag)
    private ImagesData imagesData;

    public CharactersData() {
    }

    public CharactersData(Integer characterId, String characterName, ImagesData imagesData) {
        this.characterId = characterId;
        this.characterName = characterName;
        this.imagesData = imagesData;
    }

    public String getCharacterImage() {
        return this.imagesData.getIconImageUrl();
    }

    public Integer getCharacterId() {
        return characterId;
    }

    public void setCharacterId(Integer characterId) {
        this.characterId = characterId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public ImagesData getImagesData() {
        return imagesData;
    }

    public void setImagesData(ImagesData imagesData) {
        this.imagesData = imagesData;
    }
}
