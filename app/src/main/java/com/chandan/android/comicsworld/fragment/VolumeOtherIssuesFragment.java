package com.chandan.android.comicsworld.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.adapter.VolumeOtherIssuesAdapter;
import com.chandan.android.comicsworld.model.volumes.VolumeDetailData;

/**
 * A simple {@link Fragment} subclass.
 */
public class VolumeOtherIssuesFragment extends Fragment implements VolumeOtherIssuesAdapter.OtherIssuesClickListener {

    private RecyclerView mRecyclerView;
    private VolumeOtherIssuesAdapter mAdapter;
    private VolumeDetailData volumeDetailData;

    public VolumeOtherIssuesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_volume_other_issues, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        setupUI();
    }

    private void setupUI() {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.volume_details_issues_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new VolumeOtherIssuesAdapter(this, volumeDetailData.getOtherIssues());
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setVolumeData(VolumeDetailData volumeDetailDataList) {
        this.volumeDetailData = volumeDetailDataList;
    }

    @Override
    public void onOtherIssueClick(int clickedItemIndex) {

    }
}
