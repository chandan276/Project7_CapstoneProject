package com.chandan.android.comicsworld.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.adapter.ComicsListAdapter;
import com.chandan.android.comicsworld.model.issues.IssuesData;
import com.chandan.android.comicsworld.model.issues.IssuesDataResponse;
import com.chandan.android.comicsworld.utilities.NetworkUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ComicsListAdapter.ComicsContentClickListener {

    private RecyclerView mRecyclerView;
    private ComicsListAdapter mAdapter;

    private KProgressHUD progressIndicator;

    private List<IssuesData> issuesDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSideDrawer();
        loadRecyclerView();

        getDataFromService();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_issues:
                setTitle(R.string.issues_drawer_title);
                break;

            case R.id.nav_volumes:
                setTitle(R.string.volumes_drawer_title);
                break;

            case R.id.nav_characters:
                setTitle(R.string.characters_drawer_title);
                break;

            case R.id.nav_movies:
                setTitle(R.string.movies_drawer_title);
                break;

            case R.id.nav_favorite:
                setTitle(R.string.myfavorite_drawer_title);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Setup UI
    private void setupSideDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_issues);
        setTitle(R.string.issues_drawer_title);
    }

    private void loadRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        int columnCount = getResources().getInteger(R.integer.list_column_count);

        GridLayoutManager layoutManager = new GridLayoutManager(this, columnCount);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mAdapter = new ComicsListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void showErrorMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

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

    //Network Calls
    private void getDataFromService() {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchIssuesData(new Callback<IssuesDataResponse>() {
            @Override
            public void onResponse(Call<IssuesDataResponse> call, Response<IssuesDataResponse> response) {
                if (response.body() != null) {
                    issuesDataList = response.body().getResults();
                    mAdapter.updateComicsData(issuesDataList);
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
                hideProgressIndicator();
            }

            @Override
            public void onFailure(Call<IssuesDataResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onComicsContentClick(int clickedItemIndex) {

    }
}
