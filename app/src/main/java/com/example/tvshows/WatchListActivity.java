package com.example.tvshows;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.adapters.WatchListAdapter;
import com.example.tvshows.databinding.ActivityWatchListBinding;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.viewmodel.WatchListActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class WatchListActivity extends AppCompatActivity {

    ActivityWatchListBinding activityWatchListBinding;
    WatchListActivityViewModel watchListActivityViewModel;
    WatchListAdapter watchListAdapter;
    RecyclerView watchlistrv;
    List<TvShow> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchListBinding = DataBindingUtil.setContentView(this,R.layout.activity_watch_list);
        watchListActivityViewModel = new ViewModelProvider(this).get(WatchListActivityViewModel.class);
     activityWatchListBinding.backbutton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             onBackPressed();
         }
     });
     watchlistrv = activityWatchListBinding.tvshowsrv;
     watchlist = new ArrayList<>();






    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchList();
    }


    private void loadWatchList() {

                activityWatchListBinding.setIsLoading(true);
                watchListActivityViewModel.getTVShowinwatchlist().observe(WatchListActivity.this, new Observer<List<TvShow>>() {
                    @Override
                    public void onChanged(List<TvShow> tvShows) {

                            watchlist.addAll(tvShows);
                            activityWatchListBinding.setIsLoading(false);
                            //Toast.makeText(WatchListActivity.this,String.valueOf( tvShows.size()), Toast.LENGTH_SHORT).show();

                            watchListAdapter = new WatchListAdapter(tvShows,WatchListActivity.this,watchListActivityViewModel);
                            watchlistrv.setAdapter(watchListAdapter);
                            watchlistrv.setLayoutManager(new LinearLayoutManager(WatchListActivity.this));
                            watchlistrv.setHasFixedSize(true);

                    }
                });


                    }


}