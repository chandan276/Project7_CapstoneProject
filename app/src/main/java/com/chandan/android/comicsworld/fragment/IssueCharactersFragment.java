package com.chandan.android.comicsworld.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.activity.CharacterDetailActivity;
import com.chandan.android.comicsworld.adapter.IssueCharactersAdapter;
import com.chandan.android.comicsworld.model.issues.IssueDetailData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IssueCharactersFragment extends Fragment implements IssueCharactersAdapter.CharactersClickListener {

    private RecyclerView mRecyclerView;
    private IssueCharactersAdapter mAdapter;
    private List<IssueDetailData.CharacterCredit> characterDataList;

    private static final String CHARACTER_CREDIT_RESPONSE_TEXT_KEY = "charactercredit";

    public IssueCharactersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            characterDataList = savedInstanceState.getParcelableArrayList(CHARACTER_CREDIT_RESPONSE_TEXT_KEY);
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_issue_characters, container, false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle currentState) {
        currentState.putParcelableArrayList(CHARACTER_CREDIT_RESPONSE_TEXT_KEY,
                new ArrayList<IssueDetailData.CharacterCredit>(characterDataList));
    }

    @Override
    public void onResume() {
        super.onResume();

        setupUI();
    }

    private void setupUI() {
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.issue_details_charcters_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new IssueCharactersAdapter(this, characterDataList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setIssueCharacterData(List<IssueDetailData.CharacterCredit> characterData) {
        this.characterDataList = characterData;
    }

    @Override
    public void onIssueCharacterClick(int clickedItemIndex) {
        Intent intent = new Intent(getActivity(), CharacterDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, characterDataList.get(clickedItemIndex).getCharcterCreditId());
        startActivity(intent);
    }
}
