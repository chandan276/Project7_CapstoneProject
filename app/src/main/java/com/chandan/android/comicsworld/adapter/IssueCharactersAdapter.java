package com.chandan.android.comicsworld.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.issues.IssueDetailData;

import java.util.List;

public class IssueCharactersAdapter extends RecyclerView.Adapter<IssueCharactersAdapter.IssuesCharacterHolder> {

    final private CharactersClickListener mOnClickListener;

    private List<IssueDetailData.CharacterCredit> charatcerCreditData;

    public interface CharactersClickListener {
        void onIssueCharacterClick(int clickedItemIndex);
    }

    public IssueCharactersAdapter(CharactersClickListener mOnClickListener, List<IssueDetailData.CharacterCredit> charatcerCreditData) {
        this.mOnClickListener = mOnClickListener;
        this.charatcerCreditData = charatcerCreditData;
    }

    @NonNull
    @Override
    public IssuesCharacterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.issue_character_list_data;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new IssuesCharacterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IssuesCharacterHolder issuesCharacterHolder, int i) {
        Context context = issuesCharacterHolder.issueCharacterTextView.getContext();

        IssueDetailData.CharacterCredit characterCredit = this.charatcerCreditData.get(i);
        String titleText = characterCredit.getCharcterCreditName();
        if (titleText == null || titleText.equals("")) {
            titleText = context.getResources().getString(R.string.title_not_available);
        }
        issuesCharacterHolder.issueCharacterTextView.setText(titleText);
    }

    @Override
    public int getItemCount() {
        return charatcerCreditData.size();
    }

    class IssuesCharacterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView issueCharacterCardView;
        TextView issueCharacterTextView;

        IssuesCharacterHolder(View itemView) {
            super(itemView);

            issueCharacterCardView = (CardView) itemView.findViewById(R.id.issues_character_cardview);
            issueCharacterTextView = (TextView) itemView.findViewById(R.id.issue_details_character_name);

            issueCharacterCardView.setOnClickListener(this);
        }

        void bind(int listIndex) {

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onIssueCharacterClick(clickedPosition);
        }
    }
}
