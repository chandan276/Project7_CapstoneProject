package com.chandan.android.comicsworld.model.movies;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetailData implements Parcelable {

    private final static String Name_Tag = "name";
    private final static String Release_Date_Tag = "release_date";
    private final static String Image_Data_Tag = "image";
    private final static String Description_Tag = "description";
    private final static String Rating_Tag = "rating";
    private final static String Runtime_Tag = "runtime";
    private final static String Charcters_Tag = "characters";
    private final static String Concepts_Tag = "concepts";
    private final static String Studios_Tag = "studios";

    @SerializedName(Name_Tag)
    private String movieName;

    @SerializedName(Release_Date_Tag)
    private String movieReleaseDate;

    @SerializedName(Image_Data_Tag)
    private ImagesData imagesData;

    @SerializedName(Description_Tag)
    private String movieDescription;

    @SerializedName(Rating_Tag)
    private String movieRating;

    @SerializedName(Runtime_Tag)
    private String movieRuntime;

    @SerializedName(Charcters_Tag)
    private List<Characters> movieCharacters;

    @SerializedName(Concepts_Tag)
    private List<Concepts> movieConcepts;

    @SerializedName(Studios_Tag)
    private List<Studios> movieStudios;

    public MovieDetailData(String movieName, String movieReleaseDate, ImagesData imagesData, String movieDescription, String movieRating, String movieRuntime, List<Characters> movieCharacters, List<Concepts> movieConcepts, List<Studios> movieStudios) {
        this.movieName = movieName;
        this.movieReleaseDate = movieReleaseDate;
        this.imagesData = imagesData;
        this.movieDescription = movieDescription;
        this.movieRating = movieRating;
        this.movieRuntime = movieRuntime;
        this.movieCharacters = movieCharacters;
        this.movieConcepts = movieConcepts;
        this.movieStudios = movieStudios;
    }

    protected MovieDetailData(Parcel in) {
        movieName = in.readString();
        movieReleaseDate = in.readString();
        imagesData = in.readParcelable(ImagesData.class.getClassLoader());
        movieDescription = in.readString();
        movieRating = in.readString();
        movieRuntime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeString(movieReleaseDate);
        dest.writeParcelable(imagesData, flags);
        dest.writeString(movieDescription);
        dest.writeString(movieRating);
        dest.writeString(movieRuntime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieDetailData> CREATOR = new Creator<MovieDetailData>() {
        @Override
        public MovieDetailData createFromParcel(Parcel in) {
            return new MovieDetailData(in);
        }

        @Override
        public MovieDetailData[] newArray(int size) {
            return new MovieDetailData[size];
        }
    };

    public String getCharacters() {

        StringBuilder s = new StringBuilder(getMovieCharacters().size());
        for (int i = 0; i < getMovieCharacters().size(); i++) {
            String text = getMovieCharacters().get(i).getCharacterName() + "\n";
            s.append(text);
        }
        return s.toString();
    }

    public String getConcepts() {
        StringBuilder s = new StringBuilder(getMovieConcepts().size());
        for (int i = 0; i < getMovieConcepts().size(); i++) {
            String text = getMovieConcepts().get(i).getConceptName() + "\n";
            s.append(text);
        }
        return s.toString();
    }

    public String getStudios() {
        StringBuilder s = new StringBuilder(getMovieStudios().size());
        for (int i = 0; i < getMovieStudios().size(); i++) {
            String text = getMovieStudios().get(i).getStudioName() + "\n";
            s.append(text);
        }
        return s.toString();
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

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieRuntime() {
        return movieRuntime;
    }

    public void setMovieRuntime(String movieRuntime) {
        this.movieRuntime = movieRuntime;
    }

    public List<Characters> getMovieCharacters() {
        return movieCharacters;
    }

    public void setMovieCharacters(List<Characters> movieCharacters) {
        this.movieCharacters = movieCharacters;
    }

    public List<Concepts> getMovieConcepts() {
        return movieConcepts;
    }

    public void setMovieConcepts(List<Concepts> movieConcepts) {
        this.movieConcepts = movieConcepts;
    }

    public List<Studios> getMovieStudios() {
        return movieStudios;
    }

    public void setMovieStudios(List<Studios> movieStudios) {
        this.movieStudios = movieStudios;
    }

    private class Characters {
        private final static String Name_Tag = "name";

        @SerializedName(Name_Tag)
        private String characterName;

        public String getCharacterName() {
            return characterName;
        }

        public void setCharacterName(String characterName) {
            this.characterName = characterName;
        }
    }

    private class Concepts {
        private final static String Name_Tag = "name";

        @SerializedName(Name_Tag)
        private String conceptName;

        public String getConceptName() {
            return conceptName;
        }

        public void setConceptName(String conceptName) {
            this.conceptName = conceptName;
        }
    }

    private class Studios {
        private final static String Name_Tag = "name";

        @SerializedName(Name_Tag)
        private String studioName;

        public String getStudioName() {
            return studioName;
        }

        public void setStudioName(String studioName) {
            this.studioName = studioName;
        }
    }
}
