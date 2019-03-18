package com.chandan.android.comicsworld.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favoriteissues")
    LiveData<List<FavoriteIssues>> loadAllFavorites();

    @Insert
    void insertTask(FavoriteIssues favoriteissues);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(FavoriteIssues favoriteissues);

    @Delete
    void deleteTask(FavoriteIssues favoriteissues);

    @Query("SELECT * FROM favoriteissues WHERE issueId = :id")
    LiveData<FavoriteIssues> loadFavoriteById(int id);
}
