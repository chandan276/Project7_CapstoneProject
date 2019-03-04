package com.chandan.android.comicsworld.rest;

import com.chandan.android.comicsworld.model.characters.CharacterDetailDataResponse;
import com.chandan.android.comicsworld.model.characters.CharactersDataResponse;
import com.chandan.android.comicsworld.model.issues.IssuesDataResponse;
import com.chandan.android.comicsworld.model.movies.MoviesDataResponse;
import com.chandan.android.comicsworld.model.volumes.VolumeDetailDataResponse;
import com.chandan.android.comicsworld.model.volumes.VolumesDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ComicVineApiService {

    String PARAM_KEY = "api_key";
    String DATA_FORMAT = "format";
    String SEARCH = "filter";
    String ID = "id";

    String ISSUE_TYPE_CODE = "4000";
    String VOLUME_TYPE_CODE = "4050";
    String CHARACTER_TYPE_CODE = "4005";
    String MOVIES_TYPE_CODE = "4025";

    @GET("issues/")
    Call<IssuesDataResponse> getIssuesData(@Query(DATA_FORMAT) String dataFormat, @Query(PARAM_KEY) String apiKey);

    @GET("volumes/")
    Call<VolumesDataResponse> getVolumesData(@Query(DATA_FORMAT) String dataFormat, @Query(PARAM_KEY) String apiKey);

    @GET("characters/")
    Call<CharactersDataResponse> getCharactersData(@Query(DATA_FORMAT) String dataFormat, @Query(PARAM_KEY) String apiKey);

    @GET("movies/")
    Call<MoviesDataResponse> getMoviesData(@Query(DATA_FORMAT) String dataFormat, @Query(PARAM_KEY) String apiKey);

    @GET("issues/")
    Call<IssuesDataResponse> getSearchResultForIssues(@Query(SEARCH) String searchString,
                                             @Query(DATA_FORMAT) String dataFormat,
                                             @Query(PARAM_KEY) String apiKey);

    @GET("volumes/")
    Call<VolumesDataResponse> getSearchResultForVolumes(@Query(SEARCH) String searchString,
                                                      @Query(DATA_FORMAT) String dataFormat,
                                                      @Query(PARAM_KEY) String apiKey);

    @GET("characters/")
    Call<CharactersDataResponse> getSearchResultForCharacters(@Query(SEARCH) String searchString,
                                                      @Query(DATA_FORMAT) String dataFormat,
                                                      @Query(PARAM_KEY) String apiKey);

    @GET("movies/")
    Call<MoviesDataResponse> getSearchResultForMovies(@Query(SEARCH) String searchString,
                                                      @Query(DATA_FORMAT) String dataFormat,
                                                      @Query(PARAM_KEY) String apiKey);

    @GET("volume/" + VOLUME_TYPE_CODE + "-{id}/")
    Call<VolumeDetailDataResponse> getVolumeDetailData(@Path(ID) Integer volumeId,
                                                       @Query(DATA_FORMAT) String dataFormat,
                                                       @Query(PARAM_KEY) String apiKey);

    @GET("character/" + CHARACTER_TYPE_CODE + "-{id}/")
    Call<CharacterDetailDataResponse> getCharacterDetailData(@Path(ID) Integer characterId,
                                                             @Query(DATA_FORMAT) String dataFormat,
                                                             @Query(PARAM_KEY) String apiKey);
}
