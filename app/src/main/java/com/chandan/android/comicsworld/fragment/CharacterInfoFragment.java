package com.chandan.android.comicsworld.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.characters.CharacterDetailData;
import com.chandan.android.comicsworld.utilities.DateUtils;
import com.chandan.android.comicsworld.utilities.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharacterInfoFragment extends Fragment {

    private CharacterDetailData characterDetailDataList;

    public CharacterInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_info, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        ImageView characterImageView = (ImageView) getView().findViewById(R.id.character_details_screen);
        ImageUtils.displayImageFromUrlWithPlaceHolder(characterImageView.getContext(), characterDetailDataList.getCharacterImage(),
                characterImageView, R.drawable.image_placeholder, R.drawable.error_image_loading);

        TextView superNameTextField = (TextView) getView().findViewById(R.id.character_details_name);
        superNameTextField.setText(characterDetailDataList.getSuperName());

        TextView realNameTextField = (TextView) getView().findViewById(R.id.character_detail_real_name);
        realNameTextField.setText(characterDetailDataList.getRealName());

        TextView aliasesTextField = (TextView) getView().findViewById(R.id.character_detail_aliases);
        aliasesTextField.setText(characterDetailDataList.getAliases());

        TextView dobTextField = (TextView) getView().findViewById(R.id.character_detail_birthdate);
        if (characterDetailDataList.getBirthday() == null) {
            dobTextField.setText(R.string.not_available);
        } else {
            String dateText = DateUtils.getFormattedDate(characterDetailDataList.getBirthday(), "MMM dd, yyyy");
            dobTextField.setText(dateText);
        }

        TextView originTextField = (TextView) getView().findViewById(R.id.character_detail_origin);
        originTextField.setText(characterDetailDataList.getOriginName());

        TextView genderTextField = (TextView) getView().findViewById(R.id.character_detail_gender);
        Integer gender = characterDetailDataList.getGender();
        if (gender == 1) {
            genderTextField.setText(R.string.male_gender);
        } else {
            genderTextField.setText(R.string.female_gender);
        }

        TextView descriptionTextField = (TextView) getView().findViewById(R.id.character_details_description);
        descriptionTextField.setText(characterDetailDataList.getDescription());
    }

    public void setCharacterData(CharacterDetailData characterDetailData) {
        this.characterDetailDataList = characterDetailData;
    }
}
