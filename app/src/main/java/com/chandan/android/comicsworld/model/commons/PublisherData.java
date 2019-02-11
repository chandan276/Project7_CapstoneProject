package com.chandan.android.comicsworld.model.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PublisherData implements Parcelable {
    private final static String Name_Tag = "name";

    @SerializedName(Name_Tag)
    private String publisherName;

    protected PublisherData(Parcel in) {
        publisherName = in.readString();
    }

    public static final Creator<PublisherData> CREATOR = new Creator<PublisherData>() {
        @Override
        public PublisherData createFromParcel(Parcel in) {
            return new PublisherData(in);
        }

        @Override
        public PublisherData[] newArray(int size) {
            return new PublisherData[size];
        }
    };

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(publisherName);
    }
}
