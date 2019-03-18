package com.chandan.android.comicsworld.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.movies.MovieDetailData;
import com.chandan.android.comicsworld.model.movies.MovieDetailDataResponse;
import com.chandan.android.comicsworld.utilities.ImageUtils;
import com.chandan.android.comicsworld.utilities.NetworkUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    private Integer movieId;
    private MovieDetailData movieDetailData;
    private KProgressHUD progressIndicator;

    private static final String MOVIE_DETAIL_RESPONSE_TEXT_KEY = "moviedetail";
    private static final String MOVIE_ID_TEXT_KEY = "movieid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            movieId = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(MOVIE_DETAIL_RESPONSE_TEXT_KEY)) {
                movieDetailData = savedInstanceState.getParcelable(MOVIE_DETAIL_RESPONSE_TEXT_KEY);
                setupUI();
            }

            if (savedInstanceState.containsKey(MOVIE_ID_TEXT_KEY)) {
                movieId = savedInstanceState.getInt(MOVIE_ID_TEXT_KEY);
            }
        } else {
            getMovieDetails();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(MOVIE_ID_TEXT_KEY, movieId);
        outState.putParcelable(MOVIE_DETAIL_RESPONSE_TEXT_KEY, movieDetailData);
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

    private void getMovieDetails() {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchMovieDetailsData(movieId, new Callback<MovieDetailDataResponse>() {
            @Override
            public void onResponse(Call<MovieDetailDataResponse> call, Response<MovieDetailDataResponse> response) {
                if (response.body() != null) {
                    movieDetailData = response.body().getResults();
                    setupUI();
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
                hideProgressIndicator();
            }

            @Override
            public void onFailure(Call<MovieDetailDataResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void setupUI() {
        ImageView movieImageView = (ImageView) findViewById(R.id.movie_details_screen);
        ImageUtils.displayImageFromUrlWithPlaceHolder(movieImageView.getContext(), movieDetailData.getImagesData().getSuperImageUrl(),
                movieImageView, R.drawable.image_placeholder, R.drawable.error_image_loading);

        TextView movieNameTextField = (TextView) findViewById(R.id.movie_details_name);
        String textFieldText = movieDetailData.getMovieName();
        if (textFieldText == null || textFieldText.equals("")) {
            movieNameTextField.setText(R.string.not_available);
        } else {
            movieNameTextField.setText(textFieldText);
        }

        TextView releaseTextField = (TextView) findViewById(R.id.movie_detail_release_date);
        textFieldText = movieDetailData.getMovieReleaseDate();
        if (textFieldText == null || textFieldText.equals("")) {
            releaseTextField.setText(R.string.not_available);
        } else {
            releaseTextField.setText(textFieldText);
        }

        TextView ratingTextField = (TextView) findViewById(R.id.movie_detail_rating);
        textFieldText = movieDetailData.getMovieRating();
        if (textFieldText == null || textFieldText.equals("")) {
            ratingTextField.setText(R.string.not_available);
        } else {
            ratingTextField.setText(textFieldText);
        }

        TextView runtimeTextField = (TextView) findViewById(R.id.movie_detail_runtime);
        textFieldText = movieDetailData.getMovieRuntime();
        if (textFieldText == null || textFieldText.equals("")) {
            runtimeTextField.setText(R.string.not_available);
        } else {
            String formattedString = textFieldText + R.string.movie_details_minutes;
            runtimeTextField.setText(formattedString);
        }

        TextView movieCharactersTextField = (TextView) findViewById(R.id.movie_detail_charatcers);
        textFieldText = movieDetailData.getCharacters();
        if (textFieldText == null || textFieldText.equals("")) {
            movieCharactersTextField.setText(R.string.not_available);
        } else {
            movieCharactersTextField.setText(textFieldText);
        }

        TextView conceptsTextField = (TextView) findViewById(R.id.movie_detail_concepts);
        textFieldText = movieDetailData.getConcepts();
        if (textFieldText == null || textFieldText.equals("")) {
            conceptsTextField.setText(R.string.not_available);
        } else {
            conceptsTextField.setText(textFieldText);
        }

        TextView studiosTextField = (TextView) findViewById(R.id.movie_detail_studio);
        textFieldText = movieDetailData.getStudios();
        if (textFieldText == null || textFieldText.equals("")) {
            studiosTextField.setText(R.string.not_available);
        } else {
            studiosTextField.setText(textFieldText);
        }

        TextView descriptionTextField = (TextView) findViewById(R.id.movie_details_description);
        textFieldText = movieDetailData.getMovieDescription();
        if (textFieldText == null || textFieldText.equals("")) {
            descriptionTextField.setText(R.string.not_available);
        } else {
            descriptionTextField.setText(Html.fromHtml(textFieldText));
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
}
