package com.chandan.android.comicsworld.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chandan.android.comicsworld.R;
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

public class IssueDetailActivity extends AppCompatActivity {

    private KProgressHUD progressIndicator;
    private IssueDetailData issueDetailData;
    private IssuesData issuesData;

    private Menu menu;
    boolean isDataAvailable = false;

    private AppDatabase mDb;

    private static final String ISSUE_DATA_TEXT_KEY = "issuedata";
    private static final String ISSUE_DETAIL_RESPONSE_TEXT_KEY = "issuedetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);

        mDb = AppDatabase.getInstance(getApplicationContext());

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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(ISSUE_DATA_TEXT_KEY, issuesData);
        outState.putParcelable(ISSUE_DETAIL_RESPONSE_TEXT_KEY, issueDetailData);
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

            IssueDetailViewModelFactory factory = new IssueDetailViewModelFactory(mDb, issuesData.getIssuesId());
            final IssueDetailViewModel viewModel
                    = ViewModelProviders.of(this, factory).get(IssueDetailViewModel.class);

            viewModel.getTask().observe(this, new Observer<FavoriteIssues>() {
                @Override
                public void onChanged(@Nullable final FavoriteIssues favoriteIssues) {
                    viewModel.getTask().removeObserver(this);
                    MenuItem menuItem = menu.findItem(R.id.action_my_favorite);

                    if (favoriteIssues == null) {

                        final FavoriteIssues favoriteIssue = new FavoriteIssues(issuesData.getIssuesId(),
                                issuesData.getIssuesName(),
                                issuesData.getIssuesAddedDate(), issuesData.getImagesData().getMediumImageUrl(),
                                issuesData.getIssuesNumber());

                        menuItem.setIcon(R.drawable.ic_favorite_white_selected);

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.favoriteDao().insertTask(favoriteIssue);
                                finish();
                            }
                        });
                    } else {

                        menuItem.setIcon(R.drawable.ic_favorite_white_unselected);

                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                mDb.favoriteDao().deleteTask(favoriteIssues);
                                finish();
                            }
                        });
                    }

                    FavoriteIssuesWidget.sendRefreshBroadcast(IssueDetailActivity.this);
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadFavoriteButton() {
        IssueDetailViewModelFactory factory = new IssueDetailViewModelFactory(mDb, issuesData.getIssuesId());
        final IssueDetailViewModel viewModel
                = ViewModelProviders.of(this, factory).get(IssueDetailViewModel.class);

        viewModel.getTask().observe(this, new Observer<FavoriteIssues>() {
            @Override
            public void onChanged(@Nullable FavoriteIssues favoriteIssues) {
                viewModel.getTask().removeObserver(this);
                MenuItem menuItem = menu.findItem(R.id.action_my_favorite);
                if (favoriteIssues == null) {
                    menuItem.setIcon(R.drawable.ic_favorite_white_unselected);
                } else {
                    menuItem.setIcon(R.drawable.ic_favorite_white_selected);
                }
            }
        });
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
}
