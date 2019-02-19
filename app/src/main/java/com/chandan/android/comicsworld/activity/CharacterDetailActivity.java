package com.chandan.android.comicsworld.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.fragment.CharacterGalleryFragment;
import com.chandan.android.comicsworld.fragment.CharacterInfoFragment;
import com.chandan.android.comicsworld.model.characters.CharacterDetailData;
import com.chandan.android.comicsworld.model.characters.CharacterDetailDataResponse;
import com.chandan.android.comicsworld.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends AppCompatActivity {

    private List<CharacterDetailData> characterDetailDataList = new ArrayList<>();
    private Integer characterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            characterId = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        getCharacterDetails();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CharacterInfoFragment(), "INFO");
        adapter.addFragment(new CharacterGalleryFragment(), "GALLERY");
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

    private void getCharacterDetails() {
        NetworkUtils.fetchCharacterDetailsData(characterId, new Callback<CharacterDetailDataResponse>() {
            @Override
            public void onResponse(Call<CharacterDetailDataResponse> call, Response<CharacterDetailDataResponse> response) {
                if (response.body() != null) {
                    characterDetailDataList = response.body().getResults();
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
            }

            @Override
            public void onFailure(Call<CharacterDetailDataResponse> call, Throwable t) {
                showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
