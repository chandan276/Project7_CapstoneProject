package com.chandan.android.comicsworld.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chandan.android.comicsworld.data.FavoriteContract.IssueEntry;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "favoriteDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE "  + IssueEntry.TABLE_NAME + " (" +
                IssueEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                IssueEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                IssueEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                IssueEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                IssueEntry.COLUMN_NUMBER + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + IssueEntry.TABLE_NAME);
        onCreate(db);
    }
}

