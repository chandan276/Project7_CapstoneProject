package com.chandan.android.comicsworld.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.volumes.VolumesData;
import com.chandan.android.comicsworld.utilities.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class VolumesListAdapter extends RecyclerView.Adapter<VolumesListAdapter.VolumesListHolder> {

    final private VolumesClickListener mOnClickListener;

    private List<VolumesData> volumesDataList = new ArrayList<>();

    public interface VolumesClickListener {
        void onVolumesContentClick(int clickedItemIndex);
    }

    public VolumesListAdapter(VolumesClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void updateVolumesData(List<VolumesData> volumesData) {
        this.volumesDataList = volumesData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VolumesListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.volumes_list_data;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new VolumesListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumesListHolder volumesListHolder, int i) {
        Context context = volumesListHolder.volumesImageView.getContext();
        VolumesData volumesData = volumesDataList.get(i);

        volumesListHolder.volumeNameTextView.setText(volumesData.getVolumesName());

        String issueText = context.getString(R.string.issue_number) + volumesData.getVolumesIssueCount();
        volumesListHolder.issuesTextView.setText(issueText);

        String publisherText = context.getString(R.string.publisher_text) + " " + volumesData.getPublisherName();
        volumesListHolder.publisherNameTextView.setText(publisherText);

        ImageUtils.displayImageFromUrlWithPlaceHolder(context, volumesData.getVolumesImage(),
                volumesListHolder.volumesImageView, R.drawable.image_placeholder, R.drawable.error_image_loading);
    }

    @Override
    public int getItemCount() {
        return volumesDataList.size();
    }

    class VolumesListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView volumesImageView;
        TextView volumeNameTextView;
        TextView issuesTextView;
        TextView publisherNameTextView;

        VolumesListHolder(View itemView) {
            super(itemView);

            volumesImageView = (ImageView) itemView.findViewById(R.id.volumes_imageView);
            volumeNameTextView = (TextView) itemView.findViewById(R.id.volume_name_textview);
            issuesTextView = (TextView) itemView.findViewById(R.id.issue_textview);
            publisherNameTextView = (TextView) itemView.findViewById(R.id.publisher_name_textview);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onVolumesContentClick(clickedPosition);
        }
    }
}
