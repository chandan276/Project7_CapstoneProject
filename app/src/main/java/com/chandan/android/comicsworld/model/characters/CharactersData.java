package com.chandan.android.comicsworld.model.characters;

import android.os.Parcel;
import android.os.Parcelable;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.google.gson.annotations.SerializedName;

public class CharactersData implements Parcelable {

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

    protected CharactersData(Parcel in) {
        if (in.readByte() == 0) {
            characterId = null;
        } else {
            characterId = in.readInt();
        }
        characterName = in.readString();
        imagesData = in.readParcelable(ImagesData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (characterId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(characterId);
        }
        dest.writeString(characterName);
        dest.writeParcelable(imagesData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CharactersData> CREATOR = new Creator<CharactersData>() {
        @Override
        public CharactersData createFromParcel(Parcel in) {
            return new CharactersData(in);
        }

        @Override
        public CharactersData[] newArray(int size) {
            return new CharactersData[size];
        }
    };

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
