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
import com.chandan.android.comicsworld.model.issues.IssueDetailData;
import com.chandan.android.comicsworld.utilities.DateUtils;
import com.chandan.android.comicsworld.utilities.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class IssueDetailFragment extends Fragment {

    private IssueDetailData issueDetailData;
    private String issueImageUrl;

    private static final String ISSUE_DETAIL_RESPONSE_TEXT_KEY = "moviedetail2";
    private static final String ISSUE_IMAGE_TEXT_KEY = "volumeimage2";

    public IssueDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            issueDetailData = savedInstanceState.getParcelable(ISSUE_DETAIL_RESPONSE_TEXT_KEY);
            issueImageUrl = savedInstanceState.getString(ISSUE_IMAGE_TEXT_KEY);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issue_detail, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putParcelable(ISSUE_DETAIL_RESPONSE_TEXT_KEY, issueDetailData);
        currentState.putString(ISSUE_IMAGE_TEXT_KEY, issueImageUrl);
    }

    @Override
    public void onResume() {
        super.onResume();

        setupUI();
    }

    private void setupUI() {
        ImageView issueImageView = (ImageView) getView().findViewById(R.id.issue_details_screen);
        ImageUtils.displayImageFromUrlWithPlaceHolder(issueImageView.getContext(), this.issueImageUrl,
                issueImageView, R.drawable.image_placeholder, R.drawable.error_image_loading);

        TextView issueNameTextField = (TextView) getView().findViewById(R.id.issue_details_full_name);
        if (issueDetailData.getIssueName() == null || issueDetailData.getIssueName().equals("")) {
            issueNameTextField.setText(R.string.not_available);
        } else {
            issueNameTextField.setText(issueDetailData.getIssueName());
        }

        TextView volumeNameTextField = (TextView) getView().findViewById(R.id.issue_details_issue_name);
        if (issueDetailData.getVolumeDetail().getVolumeName() == null || issueDetailData.getVolumeDetail().getVolumeName().equals("")) {
            volumeNameTextField.setText(R.string.not_available);
        } else {
            volumeNameTextField.setText(issueDetailData.getVolumeDetail().getVolumeName());
        }

        TextView coverDateTextField = (TextView) getView().findViewById(R.id.issue_details_cover_date);
        if (issueDetailData.getIssueCoverData() == null) {
            coverDateTextField.setText(R.string.not_available);
        } else {
            String dateText = DateUtils.getFormattedDate(issueDetailData.getIssueCoverData(), "MMM dd, yyyy");
            coverDateTextField.setText(dateText);
        }

        TextView storeDateTextField = (TextView) getView().findViewById(R.id.issue_details_store_date);
        if (issueDetailData.getIssueStoreDate() == null) {
            storeDateTextField.setText(R.string.not_available);
        } else {
            String dateText = DateUtils.getFormattedDate(issueDetailData.getIssueStoreDate(), "MMM dd, yyyy");
            storeDateTextField.setText(dateText);
        }

        TextView descTextField = (TextView) getView().findViewById(R.id.issue_details_description);
        if (issueDetailData.getIssueDescription() == null) {
            descTextField.setText(R.string.not_available);
        } else {
            descTextField.setText(Html.fromHtml(issueDetailData.getIssueDescription()));
        }
    }

    public void setIssueData(IssueDetailData issueDetail, String issueImageStr) {
        this.issueDetailData = issueDetail;
        this.issueImageUrl = issueImageStr;
    }
}
