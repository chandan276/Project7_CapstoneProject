package com.chandan.android.comicsworld.model.issues;

import android.os.Parcel;
import android.os.Parcelable;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IssueDetailData implements Parcelable {

    private final static String Name_Tag = "name";
    private final static String Issue_Number_Tag = "issue_number";
    private final static String Cover_Date_Tag = "cover_date";
    private final static String InStore_Date_Tag = "store_date";
    private final static String Volume_Name_Tag = "volume";
    private final static String Image_Tag = "image";
    private final static String Character_Credit_Tag = "character_credits";
    private final static String Description_Tag = "description";

    @SerializedName(Name_Tag)
    private String issueName;

    @SerializedName(Issue_Number_Tag)
    private String issueNumberName;

    @SerializedName(Cover_Date_Tag)
    private String issueCoverData;

    @SerializedName(InStore_Date_Tag)
    private String issueStoreDate;

    @SerializedName(Description_Tag)
    private String issueDescription;

    @SerializedName(Volume_Name_Tag)
    private VolumeDetail volumeDetail;

    @SerializedName(Image_Tag)
    private ImagesData imagesData;

    @SerializedName(Character_Credit_Tag)
    private List<CharacterCredit> characterCredits;

    public IssueDetailData(String issueName, String issueNumberName, String issueCoverData, String issueStoreDate, String issueDescription, VolumeDetail volumeDetail, ImagesData imagesData, List<CharacterCredit> characterCredits) {
        this.issueName = issueName;
        this.issueNumberName = issueNumberName;
        this.issueCoverData = issueCoverData;
        this.issueStoreDate = issueStoreDate;
        this.issueDescription = issueDescription;
        this.volumeDetail = volumeDetail;
        this.imagesData = imagesData;
        this.characterCredits = characterCredits;
    }

    protected IssueDetailData(Parcel in) {
        issueName = in.readString();
        issueNumberName = in.readString();
        issueCoverData = in.readString();
        issueStoreDate = in.readString();
        issueDescription = in.readString();
        imagesData = in.readParcelable(ImagesData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(issueName);
        dest.writeString(issueNumberName);
        dest.writeString(issueCoverData);
        dest.writeString(issueStoreDate);
        dest.writeString(issueDescription);
        dest.writeParcelable(imagesData, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IssueDetailData> CREATOR = new Creator<IssueDetailData>() {
        @Override
        public IssueDetailData createFromParcel(Parcel in) {
            return new IssueDetailData(in);
        }

        @Override
        public IssueDetailData[] newArray(int size) {
            return new IssueDetailData[size];
        }
    };

    public String getIssueName() {
        return issueName;
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName;
    }

    public String getIssueNumberName() {
        return issueNumberName;
    }

    public void setIssueNumberName(String issueNumberName) {
        this.issueNumberName = issueNumberName;
    }

    public String getIssueCoverData() {
        return issueCoverData;
    }

    public void setIssueCoverData(String issueCoverData) {
        this.issueCoverData = issueCoverData;
    }

    public String getIssueStoreDate() {
        return issueStoreDate;
    }

    public void setIssueStoreDate(String issueStoreDate) {
        this.issueStoreDate = issueStoreDate;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public VolumeDetail getVolumeDetail() {
        return volumeDetail;
    }

    public void setVolumeDetail(VolumeDetail volumeDetail) {
        this.volumeDetail = volumeDetail;
    }

    public ImagesData getImagesData() {
        return imagesData;
    }

    public void setImagesData(ImagesData imagesData) {
        this.imagesData = imagesData;
    }

    public List<CharacterCredit> getCharacterCredits() {
        return characterCredits;
    }

    public void setCharacterCredits(List<CharacterCredit> characterCredits) {
        this.characterCredits = characterCredits;
    }

    public class VolumeDetail {
        private final static String Name_Tag = "name";

        @SerializedName(Name_Tag)
        private String volumeName;

        public String getVolumeName() {
            return volumeName;
        }

        public void setVolumeName(String volumeName) {
            this.volumeName = volumeName;
        }
    }

    public class CharacterCredit implements Parcelable {
        private final static String Id_Tag = "id";
        private final static String Name_Tag = "name";

        @SerializedName(Id_Tag)
        private Integer charcterCreditId;

        @SerializedName(Name_Tag)
        private String charcterCreditName;

        protected CharacterCredit(Parcel in) {
            if (in.readByte() == 0) {
                charcterCreditId = null;
            } else {
                charcterCreditId = in.readInt();
            }
            charcterCreditName = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            if (charcterCreditId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(charcterCreditId);
            }
            dest.writeString(charcterCreditName);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public final Creator<CharacterCredit> CREATOR = new Creator<CharacterCredit>() {
            @Override
            public CharacterCredit createFromParcel(Parcel in) {
                return new CharacterCredit(in);
            }

            @Override
            public CharacterCredit[] newArray(int size) {
                return new CharacterCredit[size];
            }
        };

        public Integer getCharcterCreditId() {
            return charcterCreditId;
        }

        public void setCharcterCreditId(Integer charcterCreditId) {
            this.charcterCreditId = charcterCreditId;
        }

        public String getCharcterCreditName() {
            return charcterCreditName;
        }

        public void setCharcterCreditName(String charcterCreditName) {
            this.charcterCreditName = charcterCreditName;
        }
    }
}
