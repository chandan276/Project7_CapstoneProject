package com.chandan.android.comicsworld.model.issues;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IssueDetailData {

    private final static String Name_Tag = "name";
    private final static String Issue_Number_Tag = "issue_number";
    private final static String Cover_Date_Tag = "cover_date";
    private final static String InStore_Date_Tag = "store_date";
    private final static String Volume_Name_Tag = "volume";
    private final static String Image_Tag = "image";
    private final static String Character_Credit_Tag = "character_credits";

    @SerializedName(Name_Tag)
    private String issueName;

    @SerializedName(Issue_Number_Tag)
    private String issueNumberName;

    @SerializedName(Cover_Date_Tag)
    private String issueCoverData;

    @SerializedName(InStore_Date_Tag)
    private String issueStoreDate;

    @SerializedName(Volume_Name_Tag)
    private VolumeDetail volumeDetail;

    @SerializedName(Image_Tag)
    private ImagesData imagesData;

    @SerializedName(Character_Credit_Tag)
    private List<CharacterCredit> characterCredits;

    public IssueDetailData(String issueName, String issueNumberName, String issueCoverData, String issueStoreDate, VolumeDetail volumeDetail, ImagesData imagesData, List<CharacterCredit> characterCredits) {
        this.issueName = issueName;
        this.issueNumberName = issueNumberName;
        this.issueCoverData = issueCoverData;
        this.issueStoreDate = issueStoreDate;
        this.volumeDetail = volumeDetail;
        this.imagesData = imagesData;
        this.characterCredits = characterCredits;
    }

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

    private class VolumeDetail {
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

    private class CharacterCredit {
        private final static String Id_Tag = "id";
        private final static String Name_Tag = "name";

        @SerializedName(Id_Tag)
        private String charcterCreditId;

        @SerializedName(Name_Tag)
        private String charcterCreditName;

        public String getCharcterCreditId() {
            return charcterCreditId;
        }

        public void setCharcterCreditId(String charcterCreditId) {
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
