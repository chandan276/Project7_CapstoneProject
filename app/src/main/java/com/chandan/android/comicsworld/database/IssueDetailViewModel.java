package com.chandan.android.comicsworld.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class IssueDetailViewModel extends ViewModel {

    private LiveData<FavoriteIssues> favoriteissues;

    public IssueDetailViewModel(AppDatabase database, int issueId) {
        favoriteissues = database.favoriteDao().loadFavoriteById(issueId);
    }

    public LiveData<FavoriteIssues> getTask() {
        return favoriteissues;
    }
}
