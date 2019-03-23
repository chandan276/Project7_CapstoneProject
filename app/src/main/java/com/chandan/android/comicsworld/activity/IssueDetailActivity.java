package com.chandan.android.comicsworld.activity;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.data.FavoriteContract;
import com.chandan.android.comicsworld.database.AppDatabase;
import com.chandan.android.comicsworld.database.AppExecutors;
import com.chandan.android.comicsworld.database.FavoriteIssues;
import com.chandan.android.comicsworld.database.IssueDetailViewModel;
import com.chandan.android.comicsworld.database.IssueDetailViewModelFactory;
import com.chandan.android.comicsworld.fragment.IssueCharactersFragment;
import com.chandan.android.comicsworld.fragment.IssueDetailFragment;
import com.chandan.android.comicsworld.model.issues.IssueDetailData;
import com.chandan.android.comicsworld.model.issues.IssueDetailDataResponse;
import com.chandan.android.comicsworld.model.issues.IssuesData;
import com.chandan.android.comicsworld.utilities.NetworkUtils;
import com.chandan.android.comicsworld.widget.FavoriteIssuesWidget;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueDetailActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private KProgressHUD progressIndicator;
    private IssueDetailData issueDetailData;
    private IssuesData issuesData;

    private Menu menu;
    boolean isDataAvailable = false;

    private static final String ISSUE_DATA_TEXT_KEY = "issuedata";
    private static final String ISSUE_DETAIL_RESPONSE_TEXT_KEY = "issuedetail";

    private static final String TAG = IssueDetailActivity.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(ISSUE_DATA_TEXT_KEY)) {
            issuesData = intent.getParcelableExtra(ISSUE_DATA_TEXT_KEY);
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(ISSUE_DETAIL_RESPONSE_TEXT_KEY)) {
                issueDetailData = savedInstanceState.getParcelable(ISSUE_DETAIL_RESPONSE_TEXT_KEY);
            }

            if (savedInstanceState.containsKey(ISSUE_DATA_TEXT_KEY)) {
                issuesData = savedInstanceState.getParcelable(ISSUE_DATA_TEXT_KEY);
            }
            setupViewPager();
        } else {
            getIssueDetails();
        }

        /*
         Ensure a loader is initialized and active. If the loader doesn't already exist, one is
         created, otherwise the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(ISSUE_DATA_TEXT_KEY, issuesData);
        outState.putParcelable(ISSUE_DETAIL_RESPONSE_TEXT_KEY, issueDetailData);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // re-queries for all tasks
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.issue_detail_favorite, menu);
        loadFavoriteButton();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }  else if (id == R.id.action_my_favorite) {

            if (isDataAvailable) {
                removeIssueFromFavorite();
            } else {
                addIssueToFavorite();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void addIssueToFavorite() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                ContentValues contentValues = new ContentValues();

                contentValues.put(FavoriteContract.IssueEntry.COLUMN_ID, issuesData.getIssuesId());
                contentValues.put(FavoriteContract.IssueEntry.COLUMN_NAME,
                        issuesData.getIssuesName() == null ? "" : issuesData.getIssuesName());
                contentValues.put(FavoriteContract.IssueEntry.COLUMN_DATE,
                        issuesData.getIssuesAddedDate() == null ? "" : issuesData.getIssuesAddedDate());
                contentValues.put(FavoriteContract.IssueEntry.COLUMN_IMAGE, issuesData.getComicImage());
                contentValues.put(FavoriteContract.IssueEntry.COLUMN_NUMBER, issuesData.getIssuesNumber());

                // Insert the content values via a ContentResolver
                Uri uri = getContentResolver().insert(FavoriteContract.IssueEntry.CONTENT_URI, contentValues);

                if(uri != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            isDataAvailable = true;
                            MenuItem menuItem = menu.findItem(R.id.action_my_favorite);
                            menuItem.setIcon(R.drawable.ic_favorite_white_selected);

                            // this will send the broadcast to update the appwidget
                            FavoriteIssuesWidget.sendRefreshBroadcast(IssueDetailActivity.this);
                        }
                    });
                }

                return null;

            }
        }.execute();
    }

    private void removeIssueFromFavorite() {
        // Build appropriate uri with String row id appended
        String stringId = Integer.toString(issuesData.getIssuesId());
        Uri uri = FavoriteContract.IssueEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();

        // COMPLETED (2) Delete a single row of data using a ContentResolver
        getContentResolver().delete(uri, null, null);

        // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, IssueDetailActivity.this);

        isDataAvailable = false;
        MenuItem menuItem = menu.findItem(R.id.action_my_favorite);
        menuItem.setIcon(R.drawable.ic_favorite_white_unselected);

        FavoriteIssuesWidget.sendRefreshBroadcast(IssueDetailActivity.this);
    }

    private void loadFavoriteButton() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                Cursor cursor = null;

                try {
                    cursor = getContentResolver().query(FavoriteContract.IssueEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            FavoriteContract.IssueEntry.COLUMN_ID);
                }
                catch (Exception e) {

                }

                if (cursor != null) {
                    while(cursor.moveToNext()) {
                        int issueId = cursor.getInt(0);
                        if (issueId == issuesData.getIssuesId()) {
                            isDataAvailable = true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MenuItem menuItem = menu.findItem(R.id.action_my_favorite);
                                    menuItem.setIcon(R.drawable.ic_favorite_white_selected);
                                }
                            });
                        }
                    }
                }

                if (cursor != null) {
                    cursor.close();
                }

                return null;
            }
        }.execute();
    }

    private void setupViewPager(ViewPager viewPager) {
        IssueDetailActivity.ViewPagerAdapter adapter = new IssueDetailActivity.ViewPagerAdapter(getSupportFragmentManager());

        IssueDetailFragment issueDetailFragment = new IssueDetailFragment();
        issueDetailFragment.setIssueData(this.issueDetailData, issuesData.getComicImage());
        adapter.addFragment(issueDetailFragment, "DETAILS");

        IssueCharactersFragment issueCharacterFragment = new IssueCharactersFragment();
        issueCharacterFragment.setIssueCharacterData(this.issueDetailData.getCharacterCredits());
        adapter.addFragment(issueCharacterFragment, "CHARACTERS");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void getIssueDetails() {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchIssueDetailData(issuesData.getIssuesId(), new Callback<IssueDetailDataResponse>() {
            @Override
            public void onResponse(Call<IssueDetailDataResponse> call, Response<IssueDetailDataResponse> response) {
                if (response.body() != null) {
                    issueDetailData = response.body().getResults();
                    setupViewPager();
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
                hideProgressIndicator();
            }

            @Override
            public void onFailure(Call<IssueDetailDataResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void setupViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    //Progress Indicator
    public void showProgressIndicator(Context context, String titleLabel, String detailLabel, boolean isCancellable) {
        if (context == null) {
            return;
        }

        progressIndicator = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(isCancellable)
                .setAnimationSpeed(R.integer.progress_animation_speed)
                .setDimAmount(R.integer.progress_dimension)
                .show();

        if (titleLabel != null && !titleLabel.equals("")) {
            progressIndicator.setLabel(titleLabel);
        }

        if (detailLabel != null && !detailLabel.equals("")) {
            progressIndicator.setDetailsLabel(detailLabel);
        }
    }

    public void hideProgressIndicator() {
        progressIndicator.dismiss();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return getContentResolver().query(FavoriteContract.IssueEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            FavoriteContract.IssueEntry.COLUMN_ID);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };

    }


    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolder
    }


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
