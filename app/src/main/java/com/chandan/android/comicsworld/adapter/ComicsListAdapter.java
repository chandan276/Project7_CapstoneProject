package com.chandan.android.comicsworld.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.model.issues.IssuesData;
import com.chandan.android.comicsworld.utilities.DateUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ComicsListAdapter extends RecyclerView.Adapter<ComicsListAdapter.ComicsListHolder> {

    final private ComicsContentClickListener mOnClickListener;

    private List<IssuesData> issuesDataList = new ArrayList<>();

    public interface ComicsContentClickListener {
        void onComicsContentClick(int clickedItemIndex);
    }

    public ComicsListAdapter(ComicsContentClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void updateComicsData(List<IssuesData> issuesData) {
        this.issuesDataList = issuesData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ComicsListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.comics_list_data;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new ComicsListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ComicsListHolder comicsListHolder, int i) {
        Context context = comicsListHolder.contentImageView.getContext();
        IssuesData movieModel = issuesDataList.get(i);

        String titleText = "Issue #" + movieModel.getIssuesNumber();
        comicsListHolder.titleTextView.setText(titleText);

        String subTitleText = DateUtils.getFormattedDate(movieModel.getIssuesAddedDate(), "MMM dd, yyyy");
        comicsListHolder.subTitleTextView.setText(subTitleText);

        //ImageUtils.loadImageWithProgress(comicsListHolder.contentImageView, movieModel.getComicImage(), comicsListHolder.progressBar);

//        Glide.with(this.)
//                .load(movieModel.getComicImage())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .centerCrop()
//                .dontAnimate()
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        Bitmap bitmap = ((GlideBitmapDrawable) resource.getCurrent()).getBitmap();
//                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//                            @Override
//                            public void onGenerated(Palette palette) {
//                                int defaultColor = 0xFF333333;
//                                int color = palette.getDarkVibrantColor(defaultColor);
//                                comicsListHolder.metaBar.setBackgroundColor(color);
//                            }
//                        });
//
//
//                        return false;
//                    }
//                })
//                .into(comicsListHolder.contentImageView);

        Picasso.with(context)
                .load(movieModel.getComicImage())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(comicsListHolder.contentImageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
        comicsListHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return issuesDataList.size();
    }

    class ComicsListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout metaBar;
        ImageView contentImageView;
        TextView titleTextView;
        TextView subTitleTextView;
        ProgressBar progressBar;

        ComicsListHolder(View itemView) {
            super(itemView);

            contentImageView = (ImageView) itemView.findViewById(R.id.content_imageView);
            titleTextView = (TextView) itemView.findViewById(R.id.content_title);
            subTitleTextView = (TextView) itemView.findViewById(R.id.content_subtitle);

            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onComicsContentClick(clickedPosition);
        }
    }
}
