package com.chandan.android.comicsworld.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.characters.CharacterDetailData;
import com.chandan.android.comicsworld.model.characters.CharacterDetailDataResponse;
import com.chandan.android.comicsworld.utilities.DateUtils;
import com.chandan.android.comicsworld.utilities.ImageUtils;
import com.chandan.android.comicsworld.utilities.NetworkUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterDetailActivity extends AppCompatActivity {

    private CharacterDetailData characterDetailDataList;
    private Integer characterId;
    private KProgressHUD progressIndicator;

    private static final String CHARACTER_DETAIL_RESPONSE_TEXT_KEY = "characterdetail";
    private static final String CHARACTER_ID_TEXT_KEY = "characterid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            characterId = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(CHARACTER_DETAIL_RESPONSE_TEXT_KEY)) {
                characterDetailDataList = savedInstanceState.getParcelable(CHARACTER_DETAIL_RESPONSE_TEXT_KEY);
                setupUI();
            }

            if (savedInstanceState.containsKey(CHARACTER_ID_TEXT_KEY)) {
                characterId = savedInstanceState.getInt(CHARACTER_ID_TEXT_KEY);
            }
        } else {
            getCharacterDetails();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(CHARACTER_ID_TEXT_KEY, characterId);
        outState.putParcelable(CHARACTER_DETAIL_RESPONSE_TEXT_KEY, characterDetailDataList);
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

    private void getCharacterDetails() {
        showProgressIndicator(this, getString(R.string.progress_indicator_home_label), getString(R.string.progress_indicator_home_detail_label), true);
        NetworkUtils.fetchCharacterDetailsData(characterId, new Callback<CharacterDetailDataResponse>() {
            @Override
            public void onResponse(Call<CharacterDetailDataResponse> call, Response<CharacterDetailDataResponse> response) {
                if (response.body() != null) {
                    characterDetailDataList = response.body().getResults();
                    setupUI();
                } else {
                    showErrorMessage(getString(R.string.network_error));
                }
                hideProgressIndicator();
            }

            @Override
            public void onFailure(Call<CharacterDetailDataResponse> call, Throwable t) {
                hideProgressIndicator();
                showErrorMessage(t.getLocalizedMessage());
            }
        });
    }

    private void setupUI() {
        ImageView characterImageView = (ImageView) findViewById(R.id.character_details_screen);
        ImageUtils.displayImageFromUrlWithPlaceHolder(characterImageView.getContext(), characterDetailDataList.getCharacterImage(),
                characterImageView, R.drawable.image_placeholder, R.drawable.error_image_loading);

        TextView superNameTextField = (TextView) findViewById(R.id.character_details_name);
        superNameTextField.setText(characterDetailDataList.getSuperName());

        TextView realNameTextField = (TextView) findViewById(R.id.character_detail_real_name);
        realNameTextField.setText(characterDetailDataList.getRealName());

        TextView aliasesTextField = (TextView) findViewById(R.id.character_detail_aliases);
        aliasesTextField.setText(characterDetailDataList.getAliases());

        TextView dobTextField = (TextView) findViewById(R.id.character_detail_birthdate);
        if (characterDetailDataList.getBirthday() == null) {
            dobTextField.setText(R.string.not_available);
        } else {
            String dateText = DateUtils.getFormattedDate(characterDetailDataList.getBirthday(), "MMM dd, yyyy");
            dobTextField.setText(dateText);
        }

        TextView originTextField = (TextView) findViewById(R.id.character_detail_origin);
        originTextField.setText(characterDetailDataList.getOriginName());

        TextView genderTextField = (TextView) findViewById(R.id.character_detail_gender);
        Integer gender = characterDetailDataList.getGender();
        if (gender == 1) {
            genderTextField.setText(R.string.male_gender);
        } else {
            genderTextField.setText(R.string.female_gender);
        }

        TextView descriptionTextField = (TextView) findViewById(R.id.character_details_description);
        descriptionTextField.setText(characterDetailDataList.getDescription());
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
