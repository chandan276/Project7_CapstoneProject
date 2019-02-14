package com.chandan.android.comicsworld.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import com.chandan.android.comicsworld.adapter.CharactersListAdapter;
import com.chandan.android.comicsworld.adapter.ComicsListAdapter;
import com.chandan.android.comicsworld.adapter.MoviesListAdapter;
import com.chandan.android.comicsworld.adapter.VolumesListAdapter;
import com.chandan.android.comicsworld.model.characters.CharactersData;
import com.chandan.android.comicsworld.model.characters.CharactersDataResponse;
import com.chandan.android.comicsworld.model.issues.IssuesData;
import com.chandan.android.comicsworld.model.issues.IssuesDataResponse;
import com.chandan.android.comicsworld.model.movies.MoviesData;
import com.chandan.android.comicsworld.model.movies.MoviesDataResponse;
import com.chandan.android.comicsworld.model.volumes.VolumesData;
import com.chandan.android.comicsworld.model.volumes.VolumesDataResponse;
import com.chandan.android.comicsworld.utilities.NetworkUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

enum ScreenType {
    ISSUES,
    VOLUMES,
    CHARACTERS,
    MOVIES,
    FAVORITE
}

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ComicsListAdapter.ComicsContentClickListener,
        VolumesListAdapter.VolumesClickListener, MoviesListAdapter.MoviesClickListener, CharactersListAdapter.CharacterContentClickListener {

    private RecyclerView mRecyclerView;

    private ComicsListAdapter issuesListAdapter;
    private VolumesListAdapter volumesListAdapter;
    private MoviesListAdapter moviesListAdapter;
    private CharactersListAdapter charactersListAdapter;

    private KProgressHUD progressIndicator;
    ScreenType screenType = ScreenType.ISSUES;

    private List<IssuesData> issuesDataList = new ArrayList<>();
    private List<VolumesData> volumesDataList = new ArrayList<>();
    private List<MoviesData> moviesDataList = new ArrayList<>();
    private List<CharactersData> charactersDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSideDrawer();
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
                screenType = ScreenType.ISSUES;
                break;

            case R.id.nav_volumes:
                setTitle(R.string.volumes_drawer_title);
                screenType = ScreenType.VOLUMES;
                break;

            case R.id.nav_characters:
                setTitle(R.string.characters_drawer_title);
                screenType = ScreenType.CHARACTERS;
                break;

            case R.id.nav_movies:
                setTitle(R.string.movies_drawer_title);
                screenType = ScreenType.MOVIES;
                break;

            case R.id.nav_favorite:
                setTitle(R.string.myfavorite_drawer_title);
                screenType = ScreenType.FAVORITE;
                break;
        }

        loadRecyclerView();

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
        loadRecyclerView();
    }

    private void loadRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        int columnCount = getResources().getInteger(R.integer.list_column_count);

        if (screenType == ScreenType.CHARACTERS) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                    layoutManager.getOrientation());
            mRecyclerView.addItemDecoration(mDividerItemDecoration);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(this, columnCount);
            mRecyclerView.setLayoutManager(layoutManager);
        }

        mRecyclerView.setHasFixedSize(true);

        setRespectiveAdapter();
    }

    private void setRespectiveAdapter() {
        switch (screenType) {
            case ISSUES:
                issuesListAdapter = new ComicsListAdapter(this);
                mRecyclerView.setAdapter(issuesListAdapter);
                getDataForIssues();
                break;

            case VOLUMES:
                volumesListAdapter = new VolumesListAdapter(this);
                mRecyclerView.setAdapter(volumesListAdapter);
                getDataForVolumes();
                break;

            case CHARACTERS:
                charactersListAdapter = new CharactersListAdapter(this);
                mRecyclerView.setAdapter(charactersListAdapter);
                getDataForCharacters();
                break;

            case MOVIES:
                moviesListAdapter = new MoviesListAdapter(this);
                mRecyclerView.setAdapter(moviesListAdapter);
                getDataForMovies();
                break;

            case FAVORITE:
                break;
        }
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
    private void getDataForIssues() {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchIssuesData(new Callback<IssuesDataResponse>() {
            @Override
            public void onResponse(Call<IssuesDataResponse> call, Response<IssuesDataResponse> response) {
                if (response.body() != null) {
                    issuesDataList = response.body().getResults();
                    issuesListAdapter.updateComicsData(issuesDataList);
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

    private void getDataForVolumes() {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchVolumesData(new Callback<VolumesDataResponse>() {
            @Override
            public void onResponse(Call<VolumesDataResponse> call, Response<VolumesDataResponse> response) {
                if (response.body() != null) {
                    volumesDataList = response.body().getResults();
                    volumesListAdapter.updateVolumesData(volumesDataList);
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
                hideProgressIndicator();
            }

            @Override
            public void onFailure(Call<VolumesDataResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void getDataForMovies() {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchMoviesData(new Callback<MoviesDataResponse>() {
            @Override
            public void onResponse(Call<MoviesDataResponse> call, Response<MoviesDataResponse> response) {
                if (response.body() != null) {
                    moviesDataList = response.body().getResults();
                    moviesListAdapter.updateVolumesData(moviesDataList);
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
                hideProgressIndicator();
            }

            @Override
            public void onFailure(Call<MoviesDataResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void getDataForCharacters() {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchCharactersData(new Callback<CharactersDataResponse>() {
            @Override
            public void onResponse(Call<CharactersDataResponse> call, Response<CharactersDataResponse> response) {
                if (response.body() != null) {
                    charactersDataList = response.body().getResults();
                    charactersListAdapter.updateCharactersData(charactersDataList);
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
                hideProgressIndicator();
            }

            @Override
            public void onFailure(Call<CharactersDataResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    //Click Callbacks
    @Override
    public void onComicsContentClick(int clickedItemIndex) {

    }

    @Override
    public void onVolumesContentClick(int clickedItemIndex) {

    }

    @Override
    public void onMoviesContentClick(int clickedItemIndex) {

    }

    @Override
    public void onCharacterContentClick(int clickedItemIndex) {

    }
}