package com.chandan.android.comicsworld.rest;

import com.chandan.android.comicsworld.model.characters.CharactersDataResponse;
import com.chandan.android.comicsworld.model.issues.IssuesDataResponse;
import com.chandan.android.comicsworld.model.movies.MoviesDataResponse;
import com.chandan.android.comicsworld.model.volumes.VolumesDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ComicVineApiService {

    String PARAM_KEY = "api_key";
    String DATA_FORMAT = "format";

    @GET("issues/")
    Call<IssuesDataResponse> getIssuesData(@Query(DATA_FORMAT) String dataFormat, @Query(PARAM_KEY) String apiKey);

    @GET("volumes/")
    Call<VolumesDataResponse> getVolumesData(@Query(DATA_FORMAT) String dataFormat, @Query(PARAM_KEY) String apiKey);

    @GET("characters/")
    Call<CharactersDataResponse> getCharactersData(@Query(DATA_FORMAT) String dataFormat, @Query(PARAM_KEY) String apiKey);

    @GET("movies/")
    Call<MoviesDataResponse> getMoviesData(@Query(DATA_FORMAT) String dataFormat, @Query(PARAM_KEY) String apiKey);
}
