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

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.fragment.CharacterGalleryFragment;
import com.chandan.android.comicsworld.fragment.CharacterInfoFragment;
import com.chandan.android.comicsworld.fragment.IssueCharactersFragment;
import com.chandan.android.comicsworld.fragment.IssueDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class IssueDetailActivity extends AppCompatActivity {

    private Integer issueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            issueId = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupViewPager(ViewPager viewPager) {
        IssueDetailActivity.ViewPagerAdapter adapter = new IssueDetailActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new IssueDetailFragment(), "DETAILS");
        adapter.addFragment(new IssueCharactersFragment(), "CHARACTERS");
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
}
