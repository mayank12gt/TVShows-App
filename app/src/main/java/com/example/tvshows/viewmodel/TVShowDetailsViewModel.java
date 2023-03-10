package com.example.tvshows.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tvshows.models.TVShowDetailsResponse;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.repo.TvShowsRepo;
import com.example.tvshows.watchlistDB.TVShowDB;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private TvShowsRepo tvShowsRepo;
    private TVShowDB tvShowDB;

    public TVShowDetailsViewModel(@NonNull Application application){
        super(application);
        tvShowsRepo = new TvShowsRepo();
        tvShowDB = TVShowDB.getTvShowDB(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String id){
        return tvShowsRepo.getTVShowDetails(id);

    }

    public void addToWatchList(TvShow tvShow){


        tvShowDB.getTVShowDao().addTVShowtoWatchList(tvShow);
    }
}
