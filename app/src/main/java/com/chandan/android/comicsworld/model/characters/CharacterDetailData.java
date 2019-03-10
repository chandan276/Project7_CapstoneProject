package com.chandan.android.comicsworld.model.characters;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.chandan.android.comicsworld.model.commons.PublisherData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CharacterDetailData {

    private final static String Super_Name_Tag = "name";
    private final static String Real_Name_Tag = "real_name";
    private final static String Aliases_Tag = "aliases";
    private final static String Image_Tag = "image";
    private final static String Publisher_Tag = "publisher";
    private final static String Creators_Tag = "creators";
    private final static String Gender_Tag = "gender";
    private final static String Character_Type_Tag = "origin";
    private final static String First_Appearence_Tag = "first_appeared_in_issue";
    private final static String Appear_In_Tag = "count_of_issue_appearances";
    private final static String Birthday_Tag = "birth";
    private final static String Powers_Tag = "powers";
    private final static String Description_Tag = "deck";

    @SerializedName(Super_Name_Tag)
    private String superName;

    @SerializedName(Real_Name_Tag)
    private String realName;

    @SerializedName(Aliases_Tag)
    private String aliases;

    @SerializedName(Image_Tag)
    private ImagesData imagesData;

    @SerializedName(Publisher_Tag)
    private PublisherData publisherData;

    @SerializedName(Gender_Tag)
    private Integer gender;

    @SerializedName(Appear_In_Tag)
    private Integer issuesAppearedCount;

    @SerializedName(Birthday_Tag)
    private String birthday;

    @SerializedName(Description_Tag)
    private String description;

    @SerializedName(Creators_Tag)
    private List<CreatorsData> creatorsArray;

    @SerializedName(Powers_Tag)
    private List<PowersData> powersArray;

    @SerializedName(Character_Type_Tag)
    private OriginData originData;

    @SerializedName(First_Appearence_Tag)
    private FirstAppearence firstAppearenceData;

    public CharacterDetailData(String superName, String realName, String aliases, ImagesData imagesData, PublisherData publisherData, Integer gender, Integer issuesAppearedCount, String birthday, String description, List<CreatorsData> creatorsArray, List<PowersData> powersArray, OriginData originData, FirstAppearence firstAppearenceData) {
        this.superName = superName;
        this.realName = realName;
        this.aliases = aliases;
        this.imagesData = imagesData;
        this.publisherData = publisherData;
        this.gender = gender;
        this.issuesAppearedCount = issuesAppearedCount;
        this.birthday = birthday;
        this.description = description;
        this.creatorsArray = creatorsArray;
        this.powersArray = powersArray;
        this.originData = originData;
        this.firstAppearenceData = firstAppearenceData;
    }

    public String getCharacterImage() {
        return this.imagesData.getSuperImageUrl();
    }

    public String getPublisherName() {
        return this.publisherData.getPublisherName();
    }

    public String getOriginName() {
        return this.originData.getOriginName();
    }

    public String getfirstAppearence() {
        return this.firstAppearenceData.getFirstAppearenceName();
    }

    public String getCreators() {
        return TextUtils.join(",", this.creatorsArray);
    }

    public String getPowers() {
        return TextUtils.join(",", this.powersArray);
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAliases() {
        return aliases;
    }

    public void setAliases(String aliases) {
        this.aliases = aliases;
    }

    public ImagesData getImagesData() {
        return imagesData;
    }

    public void setImagesData(ImagesData imagesData) {
        this.imagesData = imagesData;
    }

    public PublisherData getPublisherData() {
        return publisherData;
    }

    public void setPublisherData(PublisherData publisherData) {
        this.publisherData = publisherData;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getIssuesAppearedCount() {
        return issuesAppearedCount;
    }

    public void setIssuesAppearedCount(Integer issuesAppearedCount) {
        this.issuesAppearedCount = issuesAppearedCount;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<CreatorsData> getCreatorsArray() {
        return creatorsArray;
    }

    public void setCreatorsArray(List<CreatorsData> creatorsArray) {
        this.creatorsArray = creatorsArray;
    }

    public List<PowersData> getPowersArray() {
        return powersArray;
    }

    public void setPowersArray(List<PowersData> powersArray) {
        this.powersArray = powersArray;
    }

    public OriginData getOriginData() {
        return originData;
    }

    public void setOriginData(OriginData originData) {
        this.originData = originData;
    }

    public FirstAppearence getFirstAppearenceData() {
        return firstAppearenceData;
    }

    public void setFirstAppearenceData(FirstAppearence firstAppearenceData) {
        this.firstAppearenceData = firstAppearenceData;
    }

    private class CreatorsData {
        private final static String Name_Tag = "name";

        @SerializedName(Name_Tag)
        private String creatorsName;

        public String getCreatorsName() {
            return creatorsName;
        }

        public void setCreatorsName(String creatorsName) {
            this.creatorsName = creatorsName;
        }
    }

    private class PowersData {
        private final static String Name_Tag = "name";

        @SerializedName(Name_Tag)
        private String powerName;

        public String getPowerName() {
            return powerName;
        }

        public void setPowerName(String powerName) {
            this.powerName = powerName;
        }
    }

    private class OriginData {
        private final static String Name_Tag = "name";

        @SerializedName(Name_Tag)
        private String originName;

        public String getOriginName() {
            return originName;
        }

        public void setOriginName(String originName) {
            this.originName = originName;
        }
    }

    private class FirstAppearence {
        private final static String Name_Tag = "name";

        @SerializedName(Name_Tag)
        private String firstAppearenceName;

        public String getFirstAppearenceName() {
            return firstAppearenceName;
        }

        public void setFirstAppearenceName(String firstAppearenceName) {
            this.firstAppearenceName = firstAppearenceName;
        }
    }
}
