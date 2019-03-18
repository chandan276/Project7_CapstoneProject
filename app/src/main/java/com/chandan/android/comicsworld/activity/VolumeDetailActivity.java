package com.chandan.android.comicsworld.activity;

import android.content.Context;
import android.content.Intent;
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
import android.view.MenuItem;
import android.widget.Toast;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.fragment.VolumeInfoFragment;
import com.chandan.android.comicsworld.fragment.VolumeOtherIssuesFragment;
import com.chandan.android.comicsworld.model.volumes.VolumeDetailData;
import com.chandan.android.comicsworld.model.volumes.VolumeDetailDataResponse;
import com.chandan.android.comicsworld.utilities.NetworkUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolumeDetailActivity extends AppCompatActivity {

    private VolumeDetailData volumeDetailData;
    private Integer volumeId;
    private String volumeName;
    private String volumeImageUrl;

    private KProgressHUD progressIndicator;

    private static final String VOLUME_DETAIL_RESPONSE_TEXT_KEY = "moviedetail";
    private static final String VOLUME_ID_TEXT_KEY = "movieid";
    private static final String VOLUME_NAME_TEXT_KEY = "volumename";
    private static final String VOLUME_IMAGE_TEXT_KEY = "volumeimage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            volumeId = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
            volumeName = intent.getStringExtra(VOLUME_NAME_TEXT_KEY);
            volumeImageUrl = intent.getStringExtra(VOLUME_IMAGE_TEXT_KEY);
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(VOLUME_DETAIL_RESPONSE_TEXT_KEY)) {
                volumeDetailData = savedInstanceState.getParcelable(VOLUME_DETAIL_RESPONSE_TEXT_KEY);
            }

            if (savedInstanceState.containsKey(VOLUME_ID_TEXT_KEY)) {
                volumeId = savedInstanceState.getInt(VOLUME_ID_TEXT_KEY);
            }

            if (savedInstanceState.containsKey(VOLUME_NAME_TEXT_KEY)) {
                volumeName = savedInstanceState.getString(VOLUME_NAME_TEXT_KEY);
            }

            if (savedInstanceState.containsKey(VOLUME_IMAGE_TEXT_KEY)) {
                volumeImageUrl = savedInstanceState.getString(VOLUME_IMAGE_TEXT_KEY);
            }
            setupViewPager();
        } else {
            getVolumeDetails();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(VOLUME_ID_TEXT_KEY, volumeId);
        outState.putString(VOLUME_NAME_TEXT_KEY, volumeName);
        outState.putString(VOLUME_IMAGE_TEXT_KEY, volumeImageUrl);
        outState.putParcelable(VOLUME_DETAIL_RESPONSE_TEXT_KEY, volumeDetailData);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        VolumeDetailActivity.ViewPagerAdapter adapter = new VolumeDetailActivity.ViewPagerAdapter(getSupportFragmentManager());

        VolumeInfoFragment volumeInfoFragment = new VolumeInfoFragment();
        volumeInfoFragment.setVolumeData(volumeDetailData, volumeName, volumeImageUrl);
        adapter.addFragment(volumeInfoFragment, "INFO");

        VolumeOtherIssuesFragment volumeOtherIssuesFragment = new VolumeOtherIssuesFragment();
        volumeOtherIssuesFragment.setVolumeData(volumeDetailData);
        adapter.addFragment(volumeOtherIssuesFragment, "OTHER ISSUES");
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

    private void getVolumeDetails() {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchVolumeDetailData(volumeId, new Callback<VolumeDetailDataResponse>() {
            @Override
            public void onResponse(Call<VolumeDetailDataResponse> call, Response<VolumeDetailDataResponse> response) {
                if (response.body() != null) {
                    volumeDetailData = response.body().getResults();
                    setupViewPager();
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
                hideProgressIndicator();
            }

            @Override
            public void onFailure(Call<VolumeDetailDataResponse> call, Throwable t) {
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
