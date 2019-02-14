package com.chandan.android.comicsworld.model.movies;

import android.os.Parcel;
import android.os.Parcelable;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.google.gson.annotations.SerializedName;

public class MoviesData implements Parcelable {

    private final static String Id_Tag = "id";
    private final static String Name_Tag = "name";
    private final static String Release_Date_Tag = "release_date";
    private final static String Image_Data_Tag = "image";

    @SerializedName(Id_Tag)
    private Integer movieId;

    @SerializedName(Name_Tag)
    private String movieName;

    @SerializedName(Release_Date_Tag)
    private String movieReleaseDate;

    @SerializedName(Image_Data_Tag)
    private ImagesData imagesData;

    public MoviesData() {
    }

    public MoviesData(Integer movieId, String movieName, String movieReleaseDate, ImagesData imagesData) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.imagesData = imagesData;
    }

    protected MoviesData(Parcel in) {
        if (in.readByte() == 0) {
            movieId = null;
        } else {
            movieId = in.readInt();
        }
        movieName = in.readString();
        movieReleaseDate = in.readString();
        imagesData = in.readParcelable(ImagesData.class.getClassLoader());
    }

    public static final Creator<MoviesData> CREATOR = new Creator<MoviesData>() {
        @Override
        public MoviesData createFromParcel(Parcel in) {
            return new MoviesData(in);
        }

        @Override
        public MoviesData[] newArray(int size) {
            return new MoviesData[size];
        }
    };

    public String getMoviesImage() {
        return this.imagesData.getMediumImageUrl();
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public ImagesData getImagesData() {
        return imagesData;
    }

    public void setImagesData(ImagesData imagesData) {
        this.imagesData = imagesData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (movieId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(movieId);
        }
        dest.writeString(movieName);
        dest.writeString(movieReleaseDate);
        dest.writeParcelable(imagesData, flags);
    }
}
