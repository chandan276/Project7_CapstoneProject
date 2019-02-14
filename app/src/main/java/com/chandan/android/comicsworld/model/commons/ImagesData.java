package com.chandan.android.comicsworld.model.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImagesData implements Parcelable {
    private final static String Icon_Image_URL_Tag = "icon_url";
    private final static String Medium_Image_URL_Tag = "medium_url";

    @SerializedName(Icon_Image_URL_Tag)
    private String iconImageUrl;

    @SerializedName(Medium_Image_URL_Tag)
    private String mediumImageUrl;

    public ImagesData(String iconImageUrl, String mediumImageUrl) {
        this.iconImageUrl = iconImageUrl;
        this.mediumImageUrl = mediumImageUrl;
    }

    protected ImagesData(Parcel in) {
        iconImageUrl = in.readString();
        mediumImageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iconImageUrl);
        dest.writeString(mediumImageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImagesData> CREATOR = new Creator<ImagesData>() {
        @Override
        public ImagesData createFromParcel(Parcel in) {
            return new ImagesData(in);
        }

        @Override
        public ImagesData[] newArray(int size) {
            return new ImagesData[size];
        }
    };

    public String getIconImageUrl() {
        return iconImageUrl;
    }

    public void setIconImageUrl(String iconImageUrl) {
        this.iconImageUrl = iconImageUrl;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }
}
