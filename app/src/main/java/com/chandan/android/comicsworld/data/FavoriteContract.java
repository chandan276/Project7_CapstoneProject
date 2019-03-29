package com.chandan.android.comicsworld.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoriteContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.favoriteissuelist";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_TASKS = "favoriteissues";

    /* TaskEntry is an inner class that defines the contents of the task table */
    public static final class IssueEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();


        // Task table and column names
        public static final String TABLE_NAME = "favorite";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_ID = "issueId";
        public static final String COLUMN_NAME = "issueName";
        public static final String COLUMN_DATE = "issueAddedDate";
        public static final String COLUMN_IMAGE = "issueImagePath";
        public static final String COLUMN_NUMBER = "issueNumber";
    }
}
