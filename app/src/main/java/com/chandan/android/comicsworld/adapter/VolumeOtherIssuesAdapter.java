package com.chandan.android.comicsworld.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.volumes.VolumeDetailData;

import java.util.List;

public class VolumeOtherIssuesAdapter extends RecyclerView.Adapter<VolumeOtherIssuesAdapter.VolumeOtherIssuesHolder> {

    final private OtherIssuesClickListener mOnClickListener;

    private List<VolumeDetailData.OtherIssues> otherIssues;

    public interface OtherIssuesClickListener {
        void onOtherIssueClick(int clickedItemIndex);
    }

    public VolumeOtherIssuesAdapter(OtherIssuesClickListener mOnClickListener, List<VolumeDetailData.OtherIssues> otherIssues) {
        this.mOnClickListener = mOnClickListener;
        this.otherIssues = otherIssues;
    }

    @NonNull
    @Override
    public VolumeOtherIssuesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.volumes_otherissues_list_data;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new VolumeOtherIssuesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeOtherIssuesHolder volumeOtherIssuesHolder, int i) {
        Context context = volumeOtherIssuesHolder.otherIssueTitleTextView.getContext();

        VolumeDetailData.OtherIssues otherIssues = this.otherIssues.get(i);
        String titleText = otherIssues.getOtherIssueName();
        if (otherIssues.getOtherIssueName() == null || otherIssues.getOtherIssueName().equals("")) {
            titleText = context.getResources().getString(R.string.title_not_available);
        }
        volumeOtherIssuesHolder.otherIssueTitleTextView.setText(titleText);

        String issueText = "Issue #" + otherIssues.getOtherIssueNumber();
        volumeOtherIssuesHolder.otherIssueSubtitleTextView.setText(issueText);

        volumeOtherIssuesHolder.favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.otherIssues.size();
    }

    class VolumeOtherIssuesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView otherIssueCardView;
        TextView otherIssueTitleTextView;
        TextView otherIssueSubtitleTextView;
        ImageView favoriteImageView;

        VolumeOtherIssuesHolder(View itemView) {
            super(itemView);

            otherIssueCardView = (CardView) itemView.findViewById(R.id.volume_issues_cardview);
            otherIssueTitleTextView = (TextView) itemView.findViewById(R.id.content_title_volume);
            otherIssueSubtitleTextView = (TextView) itemView.findViewById(R.id.content_subtitle_volume);
            favoriteImageView = (ImageView) itemView.findViewById(R.id.favorite_image_volume);

            otherIssueCardView.setOnClickListener(this);
        }

        void bind(int listIndex) {

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onOtherIssueClick(clickedPosition);
        }
    }
}
