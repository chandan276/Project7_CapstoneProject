package com.chandan.android.comicsworld.database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class IssueDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mIssueId;

    public IssueDetailViewModelFactory(AppDatabase database, int issueId) {
        mDb = database;
        mIssueId = issueId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new IssueDetailViewModel(mDb, mIssueId);
    }
}