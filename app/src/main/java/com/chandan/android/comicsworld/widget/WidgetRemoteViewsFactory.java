package com.chandan.android.comicsworld.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.chandan.android.comicsworld.R;
import com.chandan.android.comicsworld.data.FavoriteContract;
import com.chandan.android.comicsworld.database.AppDatabase;
import com.chandan.android.comicsworld.utilities.DateUtils;

public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;

    public WidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (mCursor != null) {
            mCursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();

        mCursor = mContext.getContentResolver().query(FavoriteContract.IssueEntry.CONTENT_URI,
                null,
                null,
                null,
                FavoriteContract.IssueEntry.COLUMN_ID);

        AppDatabase database = AppDatabase.getInstance(mContext);
        mCursor = database.favoriteDao().getCursorAll();

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.collection_widget_list_item);

        String subTitleText = DateUtils.getFormattedDate(mCursor.getString(2), "MMM dd, yyyy");
        String tableRowStr = "Issue #" + mCursor.getString(4) + "\n" + subTitleText;
        rv.setTextViewText(R.id.widgetItemTaskNameLabel, tableRowStr);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(FavoriteIssuesWidget.EXTRA_LABEL, mCursor.getString(2));
        rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
