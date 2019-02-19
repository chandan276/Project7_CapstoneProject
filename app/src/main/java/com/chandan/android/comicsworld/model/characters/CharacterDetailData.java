package com.chandan.android.comicsworld.model.characters;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.chandan.android.comicsworld.model.commons.PublisherData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class CharacterDetailData {

    //https://comicvine.gamespot.com/lightning-lad/4005-1253/images/

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
    private final static String Description_Tag = "description";

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
    private ArrayList creatorsArray;

    @SerializedName(Powers_Tag)
    private ArrayList powersArray;

    @SerializedName(Character_Type_Tag)
    private Map characterTypeMap;

    @SerializedName(First_Appearence_Tag)
    private Map FirstAppearenceMap;

    public CharacterDetailData(String superName, String realName, String aliases, ImagesData imagesData,
                               PublisherData publisherData, Integer gender, Integer issuesAppearedCount,
                               String birthday, String description, ArrayList creatorsArray, ArrayList powersArray,
                               Map characterTypeMap, Map firstAppearenceMap) {

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
        this.characterTypeMap = characterTypeMap;
        FirstAppearenceMap = firstAppearenceMap;
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

    public ArrayList getCreatorsArray() {
        return creatorsArray;
    }

    public void setCreatorsArray(ArrayList creatorsArray) {
        this.creatorsArray = creatorsArray;
    }

    public ArrayList getPowersArray() {
        return powersArray;
    }

    public void setPowersArray(ArrayList powersArray) {
        this.powersArray = powersArray;
    }

    public Map getCharacterTypeMap() {
        return characterTypeMap;
    }

    public void setCharacterTypeMap(Map characterTypeMap) {
        this.characterTypeMap = characterTypeMap;
    }

    public Map getFirstAppearenceMap() {
        return FirstAppearenceMap;
    }

    public void setFirstAppearenceMap(Map firstAppearenceMap) {
        FirstAppearenceMap = firstAppearenceMap;
    }
}
