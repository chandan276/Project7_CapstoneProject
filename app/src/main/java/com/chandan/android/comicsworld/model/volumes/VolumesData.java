package com.chandan.android.comicsworld.model.volumes;

import android.os.Parcel;
import android.os.Parcelable;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.chandan.android.comicsworld.model.commons.PublisherData;
import com.google.gson.annotations.SerializedName;

public class VolumesData {

    private final static String Id_Tag = "id";
    private final static String Name_Tag = "name";
    private final static String Date_Added_Tag = "date_added";
    private final static String Issue_Number_Tag = "count_of_issues";
    private final static String Description_Tag = "description";
    private final static String Publisher_Name_Tag = "publisher";
    private final static String Image_Data_Tag = "image";

    @SerializedName(Id_Tag)
    private Integer volumesId;

    @SerializedName(Name_Tag)
    private String volumesName;

    @SerializedName(Date_Added_Tag)
    private String volumesAddedDate;

    @SerializedName(Issue_Number_Tag)
    private Integer volumesIssueCount;

    @SerializedName(Description_Tag)
    private String volumesDescription;

    @SerializedName(Publisher_Name_Tag)
    private PublisherData publisherData;

    @SerializedName(Image_Data_Tag)
    private ImagesData imagesData;

    public VolumesData() {
    }

    public VolumesData(Integer volumesId, String volumesName, String volumesAddedDate, Integer volumesIssueCount, String volumesDescription, PublisherData publisherData, ImagesData imagesData) {
        this.volumesId = volumesId;
        this.volumesName = volumesName;
        this.volumesAddedDate = volumesAddedDate;
        this.volumesIssueCount = volumesIssueCount;
        this.volumesDescription = volumesDescription;
        this.publisherData = publisherData;
        this.imagesData = imagesData;
    }

    public String getPublisherName() {
        return this.publisherData.getPublisherName();
    }

    public String getVolumesImage() {
        return this.imagesData.getMediumImageUrl();
    }

    public Integer getVolumesId() {
        return volumesId;
    }

    public void setVolumesId(Integer volumesId) {
        this.volumesId = volumesId;
    }

    public String getVolumesName() {
        return volumesName;
    }

    public void setVolumesName(String volumesName) {
        this.volumesName = volumesName;
    }

    public String getVolumesAddedDate() {
        return volumesAddedDate;
    }

    public void setVolumesAddedDate(String volumesAddedDate) {
        this.volumesAddedDate = volumesAddedDate;
    }

    public Integer getVolumesIssueCount() {
        return volumesIssueCount;
    }

    public void setVolumesIssueCount(Integer volumesIssueCount) {
        this.volumesIssueCount = volumesIssueCount;
    }

    public String getVolumesDescription() {
        return volumesDescription;
    }

    public void setVolumesDescription(String volumesDescription) {
        this.volumesDescription = volumesDescription;
    }

    public PublisherData getPublisherData() {
        return publisherData;
    }

    public void setPublisherData(PublisherData publisherData) {
        this.publisherData = publisherData;
    }

    public ImagesData getImagesData() {
        return imagesData;
    }

    public void setImagesData(ImagesData imagesData) {
        this.imagesData = imagesData;
    }
}
