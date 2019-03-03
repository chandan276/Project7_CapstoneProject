package com.chandan.android.comicsworld.model.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImagesData implements Parcelable {
    private final static String Icon_Image_URL_Tag = "icon_url";
    private final static String Medium_Image_URL_Tag = "medium_url";
    private final static String Screen_Image_URL_Tag = "screen_url";
    private final static String Screen_large_Image_URL_Tag = "screen_large_url";
    private final static String Small_Image_URL_Tag = "small_url";
    private final static String Super_Image_URL_Tag = "super_url";
    private final static String Thumb_Image_URL_Tag = "thumb_url";
    private final static String Tiny_Image_URL_Tag = "tiny_url";
    private final static String Original_Image_URL_Tag = "original_url";

    @SerializedName(Icon_Image_URL_Tag)
    private String iconImageUrl;

    @SerializedName(Medium_Image_URL_Tag)
    private String mediumImageUrl;

    @SerializedName(Screen_Image_URL_Tag)
    private String screenImageUrl;

    @SerializedName(Screen_large_Image_URL_Tag)
    private String screenLargeImageUrl;

    @SerializedName(Small_Image_URL_Tag)
    private String smallImageUrl;

    @SerializedName(Super_Image_URL_Tag)
    private String superImageUrl;

    @SerializedName(Thumb_Image_URL_Tag)
    private String thumbImageUrl;

    @SerializedName(Tiny_Image_URL_Tag)
    private String tinyImageUrl;

    @SerializedName(Original_Image_URL_Tag)
    private String originalImageUrl;

    public ImagesData(String iconImageUrl, String mediumImageUrl, String screenImageUrl, String screenLargeImageUrl, String smallImageUrl, String superImageUrl, String thumbImageUrl, String tinyImageUrl, String originalImageUrl) {
        this.iconImageUrl = iconImageUrl;
        this.mediumImageUrl = mediumImageUrl;
        this.screenImageUrl = screenImageUrl;
        this.screenLargeImageUrl = screenLargeImageUrl;
        this.smallImageUrl = smallImageUrl;
        this.superImageUrl = superImageUrl;
        this.thumbImageUrl = thumbImageUrl;
        this.tinyImageUrl = tinyImageUrl;
        this.originalImageUrl = originalImageUrl;
    }

    protected ImagesData(Parcel in) {
        iconImageUrl = in.readString();
        mediumImageUrl = in.readString();
        screenImageUrl = in.readString();
        screenLargeImageUrl = in.readString();
        smallImageUrl = in.readString();
        superImageUrl = in.readString();
        thumbImageUrl = in.readString();
        tinyImageUrl = in.readString();
        originalImageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iconImageUrl);
        dest.writeString(mediumImageUrl);
        dest.writeString(screenImageUrl);
        dest.writeString(screenLargeImageUrl);
        dest.writeString(smallImageUrl);
        dest.writeString(superImageUrl);
        dest.writeString(thumbImageUrl);
        dest.writeString(tinyImageUrl);
        dest.writeString(originalImageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getIconImageUrl() {
        return iconImageUrl;
    }

    public void setIconImageUrl(String iconImageUrl) {
        this.iconImageUrl = iconImageUrl;
    }

    public String getMediumImageUrl() {
        return mediumImageUrl;
    }

    public void setMediumImageUrl(String mediumImageUrl) {
        this.mediumImageUrl = mediumImageUrl;
    }

    public String getScreenImageUrl() {
        return screenImageUrl;
    }

    public void setScreenImageUrl(String screenImageUrl) {
        this.screenImageUrl = screenImageUrl;
    }

    public String getScreenLargeImageUrl() {
        return screenLargeImageUrl;
    }

    public void setScreenLargeImageUrl(String screenLargeImageUrl) {
        this.screenLargeImageUrl = screenLargeImageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
    }

    public String getSuperImageUrl() {
        return superImageUrl;
    }

    public void setSuperImageUrl(String superImageUrl) {
        this.superImageUrl = superImageUrl;
    }

    public String getThumbImageUrl() {
        return thumbImageUrl;
    }

    public void setThumbImageUrl(String thumbImageUrl) {
        this.thumbImageUrl = thumbImageUrl;
    }

    public String getTinyImageUrl() {
        return tinyImageUrl;
    }

    public void setTinyImageUrl(String tinyImageUrl) {
        this.tinyImageUrl = tinyImageUrl;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }
}
