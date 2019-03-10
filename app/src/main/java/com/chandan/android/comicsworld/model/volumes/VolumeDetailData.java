package com.chandan.android.comicsworld.model.volumes;

import android.os.Parcel;
import android.os.Parcelable;

import com.chandan.android.comicsworld.model.commons.PublisherData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VolumeDetailData {

    private final static String Start_Year_Tag = "start_year";
    private final static String Publisher_Tag = "publisher";
    private final static String Description_Tag = "description";
    private final static String Other_Issues_Tag = "issues";

    @SerializedName(Start_Year_Tag)
    private String startYear;

    @SerializedName(Publisher_Tag)
    private PublisherData publisherData;

    @SerializedName(Description_Tag)
    private String description;

    @SerializedName(Other_Issues_Tag)
    private List<OtherIssues> otherIssues;

    public VolumeDetailData() { }

    public VolumeDetailData(String startYear, PublisherData publisherData, String description, List<OtherIssues> otherIssues) {
        this.startYear = startYear;
        this.publisherData = publisherData;
        this.description = description;
        this.otherIssues = otherIssues;
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

    public List<OtherIssues> getOtherIssues() {
        return otherIssues;
    }

    public void setOtherIssues(List<OtherIssues> otherIssues) {
        this.otherIssues = otherIssues;
    }

    public class OtherIssues {
        private final static String Id_Tag = "id";
        private final static String Name_Tag = "name";
        private final static String Issue_Number_Tag = "issue_number";

        @SerializedName(Id_Tag)
        private Integer otherIssueId;

        @SerializedName(Name_Tag)
        private String otherIssueName;

        @SerializedName(Issue_Number_Tag)
        private String otherIssueNumber;

        public OtherIssues(Integer otherIssueId, String otherIssueName, String otherIssueNumber) {
            this.otherIssueId = otherIssueId;
            this.otherIssueName = otherIssueName;
            this.otherIssueNumber = otherIssueNumber;
        }

        public Integer getOtherIssueId() {
            return otherIssueId;
        }

        public void setOtherIssueId(Integer otherIssueId) {
            this.otherIssueId = otherIssueId;
        }

        public String getOtherIssueName() {
            return otherIssueName;
        }

        public void setOtherIssueName(String otherIssueName) {
            this.otherIssueName = otherIssueName;
        }

        public String getOtherIssueNumber() {
            return otherIssueNumber;
        }

        public void setOtherIssueNumber(String otherIssueNumber) {
            this.otherIssueNumber = otherIssueNumber;
        }
    }
}
