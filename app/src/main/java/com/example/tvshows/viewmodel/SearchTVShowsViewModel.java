package com.example.tvshows.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tvshows.models.TvShowsResponse;
import com.example.tvshows.repo.TvShowsRepo;

public class SearchTVShowsViewModel extends AndroidViewModel {

    TvShowsRepo repo;
    public SearchTVShowsViewModel(@NonNull Application application) {
        super(application);
         repo = new TvShowsRepo();
    }

    public LiveData<TvShowsResponse> searchTVShow(String query, int page){

        return repo.searchTvShow(query, page);
    }
}
