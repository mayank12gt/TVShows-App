package com.example.tvshows.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tvshows.models.TvShowsResponse;
import com.example.tvshows.repo.TvShowsRepo;

import java.io.Closeable;

public class MostPopularTvShowsViewModel extends AndroidViewModel {


    private TvShowsRepo tvShowsRepo;

    public MostPopularTvShowsViewModel(@NonNull Application application){
        super(application);
        tvShowsRepo = new TvShowsRepo();
    }

    public LiveData<TvShowsResponse> getMostPopularTvShows(int page){
       return tvShowsRepo.getMostPopularTvShows(page);

    }
}
