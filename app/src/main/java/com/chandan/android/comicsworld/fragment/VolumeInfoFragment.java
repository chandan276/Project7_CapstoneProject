package com.chandan.android.comicsworld.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.volumes.VolumeDetailData;
import com.chandan.android.comicsworld.utilities.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeInfoFragment extends Fragment {

    private VolumeDetailData volumeDetailData;
    private String volumeName;
    private String volumeImageUrl;

    private static final String VOLUME_DETAIL_RESPONSE_TEXT_KEY = "moviedetail2";
    private static final String VOLUME_NAME_TEXT_KEY = "volumename2";
    private static final String VOLUME_IMAGE_TEXT_KEY = "volumeimage2";

    public VolumeInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            volumeDetailData = savedInstanceState.getParcelable(VOLUME_DETAIL_RESPONSE_TEXT_KEY);
            volumeName = savedInstanceState.getString(VOLUME_NAME_TEXT_KEY);
            volumeImageUrl = savedInstanceState.getString(VOLUME_IMAGE_TEXT_KEY);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_volume_info, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putParcelable(VOLUME_DETAIL_RESPONSE_TEXT_KEY, volumeDetailData);
        currentState.putString(VOLUME_NAME_TEXT_KEY, volumeName);
        currentState.putString(VOLUME_IMAGE_TEXT_KEY, volumeImageUrl);
    }

    @Override
    public void onResume() {
        super.onResume();

        setupUI();
    }

    private void setupUI() {
        ImageView volumeImageView = (ImageView) getView().findViewById(R.id.volume_details_screen);
        ImageUtils.displayImageFromUrlWithPlaceHolder(volumeImageView.getContext(), this.volumeImageUrl,
                volumeImageView, R.drawable.image_placeholder, R.drawable.error_image_loading);

        TextView volumeNameTextField = (TextView) getView().findViewById(R.id.volume_details_name);
        volumeNameTextField.setText(this.volumeName);

        TextView publisherTextField = (TextView) getView().findViewById(R.id.volume_details_publisher);
        publisherTextField.setText(volumeDetailData.getPublisherData().getPublisherName());

        TextView yearTextField = (TextView) getView().findViewById(R.id.volume_details_year);
        yearTextField.setText(volumeDetailData.getStartYear());

        TextView descTextField = (TextView) getView().findViewById(R.id.volume_details_description);
        descTextField.setText(Html.fromHtml(volumeDetailData.getDescription()));
    }

    public void setVolumeData(VolumeDetailData volumeDetailDataList, String volumeName, String volumeImageStr) {
        this.volumeDetailData = volumeDetailDataList;
        this.volumeName = volumeName;
        this.volumeImageUrl = volumeImageStr;
    }
}
