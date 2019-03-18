package com.chandan.android.comicsworld.adapter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.activity.MainActivity;
import com.chandan.android.comicsworld.database.AppDatabase;
import com.chandan.android.comicsworld.database.AppExecutors;
import com.chandan.android.comicsworld.database.FavoriteIssues;
import com.chandan.android.comicsworld.database.IssueDetailViewModel;
import com.chandan.android.comicsworld.database.IssueDetailViewModelFactory;
import com.chandan.android.comicsworld.database.MainViewModel;
import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.chandan.android.comicsworld.model.issues.IssuesData;
import com.chandan.android.comicsworld.utilities.DateUtils;
import com.chandan.android.comicsworld.utilities.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ComicsListAdapter extends RecyclerView.Adapter<ComicsListAdapter.ComicsListHolder> {

    final private ComicsContentClickListener mOnClickListener;
    private AppDatabase mDb;
    private Context mContext;

    private List<IssuesData> issuesDataList = new ArrayList<>();

    public interface ComicsContentClickListener {
        void onComicsContentClick(int clickedItemIndex);
    }

    public ComicsListAdapter(ComicsContentClickListener mOnClickListener, AppDatabase database, Context context) {
        this.mOnClickListener = mOnClickListener;
        this.mDb = database;
        this.mContext = context;
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
    public void onBindViewHolder(@NonNull final ComicsListHolder comicsListHolder, final int i) {
        Context context = comicsListHolder.contentImageView.getContext();
        IssuesData movieModel = issuesDataList.get(i);

        String titleText = context.getString(R.string.issue_number) + movieModel.getIssuesNumber();
        comicsListHolder.titleTextView.setText(titleText);

        String subTitleText = DateUtils.getFormattedDate(movieModel.getIssuesAddedDate(), "MMM dd, yyyy");
        comicsListHolder.subTitleTextView.setText(subTitleText);

        ImageUtils.displayImageFromUrlWithPlaceHolder(context, movieModel.getComicImage(),
                comicsListHolder.contentImageView, R.drawable.image_placeholder, R.drawable.error_image_loading);

        loadFavoriteButtonForIndex(i, comicsListHolder);

        comicsListHolder.favoriteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = comicsListHolder.getAdapterPosition();
                IssueDetailViewModelFactory factory = new IssueDetailViewModelFactory(mDb,
                        issuesDataList.get(position).getIssuesId());
                final IssueDetailViewModel viewModel
                        = ViewModelProviders.of((MainActivity) mContext, factory).get(IssueDetailViewModel.class);

                viewModel.getTask().observe((MainActivity) mContext, new Observer<FavoriteIssues>() {
                    @Override
                    public void onChanged(@Nullable final FavoriteIssues favoriteIssues) {
                        viewModel.getTask().removeObserver(this);

                        if (favoriteIssues == null) {

                            final FavoriteIssues favoriteIssue = new FavoriteIssues(issuesDataList.get(i).getIssuesId(),
                                    issuesDataList.get(i).getIssuesName(),
                                    issuesDataList.get(i).getIssuesAddedDate(), issuesDataList.get(i).getImagesData().getMediumImageUrl(),
                                    issuesDataList.get(i).getIssuesNumber());

                            comicsListHolder.favoriteImageView.setSelected(true);

                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.favoriteDao().insertTask(favoriteIssue);
                                }
                            });

                        } else {
                            comicsListHolder.favoriteImageView.setSelected(false);

                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.favoriteDao().deleteTask(favoriteIssues);
                                }
                            });
                        }
                    }
                });
            }
        });
        comicsListHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return issuesDataList.size();
    }

    private void loadFavoriteButtonForIndex(int index, final ComicsListHolder comicsListHolder) {

        int position = comicsListHolder.getAdapterPosition();
        IssueDetailViewModelFactory factory = new IssueDetailViewModelFactory(mDb,
                issuesDataList.get(position).getIssuesId());
        final IssueDetailViewModel viewModel
                = ViewModelProviders.of((MainActivity) mContext, factory).get(IssueDetailViewModel.class);

        viewModel.getTask().observe((MainActivity) mContext, new Observer<FavoriteIssues>() {
            @Override
            public void onChanged(@Nullable FavoriteIssues favoriteIssues) {
                viewModel.getTask().removeObserver(this);
                if (favoriteIssues == null) {
                    comicsListHolder.favoriteImageView.setSelected(false);
                } else {
                    if (favoriteIssues.getIssueId() == issuesDataList.get(comicsListHolder.getAdapterPosition()).getIssuesId()) {
                        comicsListHolder.favoriteImageView.setSelected(true);
                    } else {
                        comicsListHolder.favoriteImageView.setSelected(false);
                    }
                }
            }
        });
    }

    public void getMyFavoriteIssuesList() {
        MainViewModel viewModel = ViewModelProviders.of((MainActivity) mContext).get(MainViewModel.class);
        viewModel.getTasks().observe((MainActivity) mContext, new Observer<List<FavoriteIssues>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteIssues> favoriteIssues) {
                if (favoriteIssues != null) {
                    issuesDataList.clear();
                    for (FavoriteIssues issues : favoriteIssues) {
                        IssuesData issueData = new IssuesData(issues.getIssueId(), issues.getIssuesName(),
                                issues.getIssuesAddedDate(),
                                new ImagesData(issues.getImagesUrlStr()), issues.getIssuesNumber());
                        issuesDataList.add(issueData);
                    }
                    notifyDataSetChanged();
                }
            }
        });
    }

    class ComicsListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView contentImageView;
        TextView titleTextView;
        TextView subTitleTextView;
        ImageView favoriteImageView;

        ComicsListHolder(View itemView) {
            super(itemView);

            contentImageView = (ImageView) itemView.findViewById(R.id.content_imageView);
            titleTextView = (TextView) itemView.findViewById(R.id.content_title);
            subTitleTextView = (TextView) itemView.findViewById(R.id.content_subtitle);
            favoriteImageView = (ImageView) itemView.findViewById(R.id.favorite_image);

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
