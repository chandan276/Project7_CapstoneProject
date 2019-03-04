package com.chandan.android.comicsworld.utilities;

import com.chandan.android.comicsworld.BuildConfig;
import com.chandan.android.comicsworld.model.characters.CharacterDetailDataResponse;
import com.chandan.android.comicsworld.model.characters.CharactersDataResponse;
import com.chandan.android.comicsworld.model.issues.IssuesDataResponse;
import com.chandan.android.comicsworld.model.movies.MoviesDataResponse;
import com.chandan.android.comicsworld.model.volumes.VolumeDetailDataResponse;
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

    public static void fetchIssuesData(String searchQuery, final Callback<IssuesDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<IssuesDataResponse> call;

        if (searchQuery == null) {
            call = comicVineApiService.getIssuesData(DATA_FORMAT, API_KEY);
        } else {
            call = comicVineApiService.getSearchResultForIssues("name:" + searchQuery,
                    DATA_FORMAT, API_KEY);
        }

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

    public static void fetchVolumesData(String searchQuery, final Callback<VolumesDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<VolumesDataResponse> call;

        if (searchQuery == null) {
            call = comicVineApiService.getVolumesData(DATA_FORMAT, API_KEY);
        } else {
            call = comicVineApiService.getSearchResultForVolumes("name:" + searchQuery,
                    DATA_FORMAT, API_KEY);
        }

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

    public static void fetchVolumeDetailData(Integer volumeId, final Callback<VolumeDetailDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<VolumeDetailDataResponse> call = comicVineApiService.getVolumeDetailData(volumeId, DATA_FORMAT, API_KEY);

        call.enqueue(new Callback<VolumeDetailDataResponse>() {

            @Override
            public void onResponse(Call<VolumeDetailDataResponse> call, Response<VolumeDetailDataResponse> response) {
                arrayCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<VolumeDetailDataResponse> call, Throwable throwable) {
                arrayCallback.onFailure(call, throwable);
            }
        });
    }

    public static void fetchCharactersData(String searchQuery, final Callback<CharactersDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<CharactersDataResponse> call;

        if (searchQuery == null) {
            call = comicVineApiService.getCharactersData(DATA_FORMAT, API_KEY);
        } else {
            call = comicVineApiService.getSearchResultForCharacters("name:" + searchQuery,
                    DATA_FORMAT, API_KEY);
        }

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

    public static void fetchCharacterDetailsData(Integer characterId, final Callback<CharacterDetailDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<CharacterDetailDataResponse> call = comicVineApiService.getCharacterDetailData(characterId, DATA_FORMAT, API_KEY);

        call.enqueue(new Callback<CharacterDetailDataResponse>() {

            @Override
            public void onResponse(Call<CharacterDetailDataResponse> call, Response<CharacterDetailDataResponse> response) {
                arrayCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<CharacterDetailDataResponse> call, Throwable throwable) {
                arrayCallback.onFailure(call, throwable);
            }
        });
    }

    public static void fetchMoviesData(String searchQuery, final Callback<MoviesDataResponse> arrayCallback) {
        if (retrofit == null) {
            initializeRetrofit();
        }
        ComicVineApiService comicVineApiService = retrofit.create(ComicVineApiService.class);

        Call<MoviesDataResponse> call;

        if (searchQuery == null) {
            call = comicVineApiService.getMoviesData(DATA_FORMAT, API_KEY);
        } else {
            call = comicVineApiService.getSearchResultForMovies("name:" + searchQuery,
                    DATA_FORMAT, API_KEY);
        }

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
