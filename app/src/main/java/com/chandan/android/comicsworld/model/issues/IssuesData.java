package com.chandan.android.comicsworld.model.issues;

import android.os.Parcel;
import android.os.Parcelable;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.google.gson.annotations.SerializedName;

public class IssuesData implements Parcelable {

    private final static String Id_Tag = "id";
    private final static String Name_Tag = "name";
    private final static String Date_Added_Tag = "date_added";
    private final static String Issue_Number_Tag = "issue_number";
    private final static String Image_Data_Tag = "image";

    @SerializedName(Id_Tag)
    private Integer issuesId;

    @SerializedName(Name_Tag)
    private String issuesName;

    @SerializedName(Date_Added_Tag)
    private String issuesAddedDate;

    @SerializedName(Image_Data_Tag)
    private ImagesData imagesData;

    @SerializedName(Issue_Number_Tag)
    private String issuesNumber;

    public IssuesData() { }

    public IssuesData(Integer issuesId, String issuesName, String issuesAddedDate, ImagesData imagesData, String issuesNumber) {
        this.issuesId = issuesId;
        this.issuesName = issuesName;
        this.issuesAddedDate = issuesAddedDate;
        this.imagesData = imagesData;
        this.issuesNumber = issuesNumber;
    }

    protected IssuesData(Parcel in) {
        if (in.readByte() == 0) {
            issuesId = null;
        } else {
            issuesId = in.readInt();
        }
        issuesName = in.readString();
        issuesAddedDate = in.readString();
        imagesData = in.readParcelable(ImagesData.class.getClassLoader());
        issuesNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (issuesId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(issuesId);
        }
        dest.writeString(issuesName);
        dest.writeString(issuesAddedDate);
        dest.writeParcelable(imagesData, flags);
        dest.writeString(issuesNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IssuesData> CREATOR = new Creator<IssuesData>() {
        @Override
        public IssuesData createFromParcel(Parcel in) {
            return new IssuesData(in);
        }

        @Override
        public IssuesData[] newArray(int size) {
            return new IssuesData[size];
        }
    };

    public String getComicImage() {
        return this.imagesData.getMediumImageUrl();
    }

    public Integer getIssuesId() {
        return issuesId;
    }

    public void setIssuesId(Integer issuesId) {
        this.issuesId = issuesId;
    }

    public String getIssuesName() {
        return issuesName;
    }

    public void setIssuesName(String issuesName) {
        this.issuesName = issuesName;
    }

    public String getIssuesAddedDate() {
        return issuesAddedDate;
    }

    public void setIssuesAddedDate(String issuesAddedDate) {
        this.issuesAddedDate = issuesAddedDate;
    }

    public ImagesData getImagesData() {
        return imagesData;
    }

    public void setImagesData(ImagesData imagesData) {
        this.imagesData = imagesData;
    }

    public String getIssuesNumber() {
        return issuesNumber;
    }

    public void setIssuesNumber(String issuesNumber) {
        this.issuesNumber = issuesNumber;
    }
}
