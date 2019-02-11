package com.chandan.android.comicsworld.utilities;

import com.chandan.android.comicsworld.BuildConfig;
import com.chandan.android.comicsworld.model.characters.CharactersDataResponse;
import com.chandan.android.comicsworld.model.issues.IssuesDataResponse;
import com.chandan.android.comicsworld.model.movies.MoviesDataResponse;
import com.chandan.android.comicsworld.model.volumes.VolumesDataResponse;
import com.chandan.android.comicsworld.rest.ComicVineApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    private final static String COMIC_VINE_BASE_URL = "https://comicvine.gamespot.com/api/";

    private final static String API_KEY = BuildConfig.COMIC_VINE_API_KEY;
    private final static String DATA_FORMAT = "json";

    private static Retrofit retrofit = null;

    private static void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(COMIC_VINE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static void fetchIssuesData(final Callback<IssuesDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<IssuesDataResponse> call = comicVineApiService.getIssuesData(DATA_FORMAT, API_KEY);

        call.enqueue(new Callback<IssuesDataResponse>() {

            @Override
            public void onResponse(Call<IssuesDataResponse> call, Response<IssuesDataResponse> response) {
                arrayCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<IssuesDataResponse> call, Throwable throwable) {
                arrayCallback.onFailure(call, throwable);
            }
        });
    }

    public static void fetchVolumesData(final Callback<VolumesDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<VolumesDataResponse> call = comicVineApiService.getVolumesData(DATA_FORMAT, API_KEY);

        call.enqueue(new Callback<VolumesDataResponse>() {

            @Override
            public void onResponse(Call<VolumesDataResponse> call, Response<VolumesDataResponse> response) {
                arrayCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<VolumesDataResponse> call, Throwable throwable) {
                arrayCallback.onFailure(call, throwable);
            }
        });
    }

    public static void fetchCharactersData(final Callback<CharactersDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<CharactersDataResponse> call = comicVineApiService.getCharactersData(DATA_FORMAT, API_KEY);

        call.enqueue(new Callback<CharactersDataResponse>() {

            @Override
            public void onResponse(Call<CharactersDataResponse> call, Response<CharactersDataResponse> response) {
                arrayCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<CharactersDataResponse> call, Throwable throwable) {
                arrayCallback.onFailure(call, throwable);
            }
        });
    }

    public static void fetchMoviesData(final Callback<MoviesDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<MoviesDataResponse> call = comicVineApiService.getMoviesData(DATA_FORMAT, API_KEY);

        call.enqueue(new Callback<MoviesDataResponse>() {

            @Override
            public void onResponse(Call<MoviesDataResponse> call, Response<MoviesDataResponse> response) {
                arrayCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<MoviesDataResponse> call, Throwable throwable) {
                arrayCallback.onFailure(call, throwable);
            }
        });
    }
}
