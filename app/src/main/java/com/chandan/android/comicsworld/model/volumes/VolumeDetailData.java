package com.chandan.android.comicsworld.model.volumes;

import android.os.Parcel;
import android.os.Parcelable;

import com.chandan.android.comicsworld.model.commons.PublisherData;
import com.google.gson.annotations.SerializedName;

public class VolumeDetailData {

    private final static String Start_Year_Tag = "start_year";
    private final static String Publisher_Tag = "publisher";
    private final static String Description_Tag = "description";

    @SerializedName(Start_Year_Tag)
    private String startYear;

    @SerializedName(Publisher_Tag)
    private PublisherData publisherData;

    @SerializedName(Description_Tag)
    private String description;

    public VolumeDetailData(String startYear, PublisherData publisherData, String description) {
        this.startYear = startYear;
        this.publisherData = publisherData;
        this.description = description;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public PublisherData getPublisherData() {
        return publisherData;
    }

    public void setPublisherData(PublisherData publisherData) {
        this.publisherData = publisherData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
