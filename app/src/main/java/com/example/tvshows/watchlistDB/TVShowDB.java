package com.example.tvshows.watchlistDB;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tvshows.models.TvShow;

@Database(entities = TvShow.class,version = 1,exportSchema = false)
public abstract class TVShowDB extends RoomDatabase {


    private static TVShowDB tvShowDB;

    public static synchronized TVShowDB getTvShowDB(Application application){
        if(tvShowDB==null){
            tvShowDB = Room.databaseBuilder(application,TVShowDB.class,"tvShowsDB").build();
        }
        return tvShowDB;
    }

    public abstract TVShowDao getTVShowDao();

}
