package com.chandan.android.comicsworld.adapter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.activity.MainActivity;
import com.chandan.android.comicsworld.data.FavoriteContract;
import com.chandan.android.comicsworld.model.commons.ImagesData;
import com.chandan.android.comicsworld.model.issues.IssuesData;
import com.chandan.android.comicsworld.utilities.DateUtils;
import com.chandan.android.comicsworld.utilities.ImageUtils;

import java.util.ArrayList;
import java.util.List;

public class ComicsListAdapter extends RecyclerView.Adapter<ComicsListAdapter.ComicsListHolder> implements
        LoaderManager.LoaderCallbacks<Cursor> {

    final private ComicsContentClickListener mOnClickListener;
    private Context mContext;

    private static final int TASK_LOADER_ID = 0;

    private List<IssuesData> issuesDataList = new ArrayList<>();

    public interface ComicsContentClickListener {
        void onComicsContentClick(int clickedItemIndex);
    }

    public ComicsListAdapter(ComicsContentClickListener mOnClickListener, Context context) {
        this.mOnClickListener = mOnClickListener;
        this.mContext = context;

        MainActivity mainActivity = (MainActivity) mContext;
        mainActivity.getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
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

        comicsListHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return issuesDataList.size();
    }

    class ComicsListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView contentImageView;
        TextView titleTextView;
        TextView subTitleTextView;

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(mContext) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;
            MainActivity mainActivity = (MainActivity) mContext;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                // Will implement to load data

                // Query and load all task data in the background; sort by priority
                // [Hint] use a try/catch block to catch any errors in loading data

                try {
                    return mainActivity.getContentResolver().query(FavoriteContract.IssueEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            FavoriteContract.IssueEntry.COLUMN_ID);

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult sends the result of the load, a Cursor, to the registered listener
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };

    }


    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update the data that the adapter uses to create ViewHolder
    }


    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable.
     * onLoaderReset removes any references this activity had to the loader's data.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void getMyFavoriteIssuesList() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                Cursor cursor = null;
                MainActivity mainActivity = (MainActivity) mContext;
                try {
                    cursor = mainActivity.getContentResolver().query(FavoriteContract.IssueEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            FavoriteContract.IssueEntry.COLUMN_ID);
                }
                catch (Exception e) {

                }

                if (cursor != null) {
                    issuesDataList.clear();
                    while(cursor.moveToNext()) {
                        IssuesData issueData = new IssuesData(cursor.getInt(0), cursor.getString(1),
                                cursor.getString(2),
                                new ImagesData(cursor.getString(3)), cursor.getString(4));
                        issuesDataList.add(issueData);
                    }
                }

                if (cursor != null) {
                    cursor.close();
                }

                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });

                return null;
            }
        }.execute();
    }
}
