package com.chandan.android.comicsworld.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.SearchView;
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
    private List<CharactersData> charactersDataList = new ArrayList<>();
    private List<MoviesData> moviesDataList = new ArrayList<>();

    private static final String ISSUE_RESPONSE_TEXT_KEY = "issuekey";
    private static final String VOLUME_RESPONSE_TEXT_KEY = "volumekey";
    private static final String CHARACTERS_RESPONSE_TEXT_KEY = "characterkey";
    private static final String MOVIES_RESPONSE_TEXT_KEY = "moviekey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSideDrawer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ISSUE_RESPONSE_TEXT_KEY, new ArrayList<IssuesData>(issuesDataList));
        outState.putParcelableArrayList(VOLUME_RESPONSE_TEXT_KEY, new ArrayList<VolumesData>(volumesDataList));
        outState.putParcelableArrayList(CHARACTERS_RESPONSE_TEXT_KEY, new ArrayList<CharactersData>(charactersDataList));
        outState.putParcelableArrayList(MOVIES_RESPONSE_TEXT_KEY, new ArrayList<MoviesData>(moviesDataList));
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

        // Retrieve the SearchView and plug it into SearchManager
        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchResults(query);
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                
                return false;
            }
        });

        return true;
    }

    private void getSearchResults(String query) {
        switch (screenType) {
            case ISSUES:
                getDataForIssues(query);
                break;

            case VOLUMES:
                getDataForVolumes(query);
                break;

            case CHARACTERS:
                getDataForCharacters(query);
                break;

            case MOVIES:
                getDataForMovies(query);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
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
                getDataForIssues(null);
                break;

            case VOLUMES:
                volumesListAdapter = new VolumesListAdapter(this);
                mRecyclerView.setAdapter(volumesListAdapter);
                getDataForVolumes(null);
                break;

            case CHARACTERS:
                charactersListAdapter = new CharactersListAdapter(this);
                mRecyclerView.setAdapter(charactersListAdapter);
                getDataForCharacters(null);
                break;

            case MOVIES:
                moviesListAdapter = new MoviesListAdapter(this);
                mRecyclerView.setAdapter(moviesListAdapter);
                getDataForMovies(null);
                break;

            case FAVORITE:
                break;
        }
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

    //Network Calls
    private void getDataForIssues(String searchQuery) {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchIssuesData(searchQuery, new Callback<IssuesDataResponse>() {
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

    private void getDataForVolumes(String searchQuery) {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchVolumesData(searchQuery, new Callback<VolumesDataResponse>() {
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

    private void getDataForCharacters(String searchQuery) {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchCharactersData(searchQuery, new Callback<CharactersDataResponse>() {
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

    private void getDataForMovies(String searchQuery) {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchMoviesData(searchQuery, new Callback<MoviesDataResponse>() {
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

    //Click Callbacks
    @Override
    public void onComicsContentClick(int clickedItemIndex) {
        IssuesData issuesData = issuesDataList.get(clickedItemIndex);
        performIntentTransition(IssueDetailActivity.class, issuesData.getIssuesId());
    }

    @Override
    public void onVolumesContentClick(int clickedItemIndex) {
        VolumesData volumesData = volumesDataList.get(clickedItemIndex);
        performIntentTransition(VolumeDetailActivity.class, volumesData.getVolumesId());
    }

    @Override
    public void onCharacterContentClick(int clickedItemIndex) {
        CharactersData characterData = charactersDataList.get(clickedItemIndex);
        performIntentTransition(CharacterDetailActivity.class, characterData.getCharacterId());
    }

    @Override
    public void onMoviesContentClick(int clickedItemIndex) {
        MoviesData moviesData = moviesDataList.get(clickedItemIndex);
        performIntentTransition(MovieDetailActivity.class, moviesData.getMovieId());
    }

    private void performIntentTransition(Class destinationActivity, Integer idExtra) {
        Intent intent = new Intent(MainActivity.this, destinationActivity);
        intent.putExtra(Intent.EXTRA_TEXT, idExtra);
        startActivity(intent);
    }
}
