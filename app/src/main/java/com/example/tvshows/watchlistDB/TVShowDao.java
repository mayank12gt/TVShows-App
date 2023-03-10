package com.example.tvshows.watchlistDB;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.tvshows.models.TvShow;

import java.util.List;

@Dao
public interface TVShowDao {

    @Query("SELECT * FROM tvShows")
    LiveData<List<TvShow>> getSavedTVShows();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTVShowtoWatchList(TvShow tvShow);

    @Delete()
    void deleteTVShowfromWatchList(TvShow tvShow);

}
