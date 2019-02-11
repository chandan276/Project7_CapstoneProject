package com.chandan.android.comicsworld.model.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImagesData implements Parcelable {
    private final static String Image_URL_Tag = "medium_url";

    @SerializedName(Image_URL_Tag)
    private String imageUrl;

    protected ImagesData(Parcel in) {
        imageUrl = in.readString();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
    }
}
