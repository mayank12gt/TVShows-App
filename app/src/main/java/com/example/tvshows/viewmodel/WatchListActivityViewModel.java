package com.example.tvshows.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tvshows.models.TvShow;
import com.example.tvshows.watchlistDB.TVShowDB;

import java.util.List;

public class WatchListActivityViewModel extends AndroidViewModel {

    private TVShowDB tvShowDB;
    public WatchListActivityViewModel(@NonNull Application application) {
        super(application);
        tvShowDB = TVShowDB.getTvShowDB(application);
    }
    public LiveData<List<TvShow>> getTVShowinwatchlist(){
        return tvShowDB.getTVShowDao().getSavedTVShows();

    }

    public void deleteTVShowfromWatchList(TvShow tvShow){
        tvShowDB.getTVShowDao().deleteTVShowfromWatchList(tvShow);
    }
}
