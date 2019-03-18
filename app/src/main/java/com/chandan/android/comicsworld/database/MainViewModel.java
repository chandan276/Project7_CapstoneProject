package com.chandan.android.comicsworld.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<FavoriteIssues>> favoriteIssues;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        favoriteIssues = database.favoriteDao().loadAllFavorites();
    }

    public LiveData<List<FavoriteIssues>> getTasks() {
        return favoriteIssues;
    }
}
