package com.example.tvshows;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.adapters.TVShowsAdapter;
import com.example.tvshows.databinding.ActivityMainBinding;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.viewmodel.MostPopularTvShowsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MostPopularTvShowsViewModel viewModel;

    private ActivityMainBinding activityMainBinding;
    private List<TvShow> tvShowList;
    TVShowsAdapter adapter;
    int currentpage=1;
    int totalPages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        tvShowList =new ArrayList<>();

        initialize();
        activityMainBinding.imagesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSearch();
            }
        });
        activityMainBinding.imagewatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),WatchListActivity.class);
                startActivity(i);
            }
        });



    }

    private void openSearch() {
        Intent i = new Intent(this,SearchActivity.class);
        startActivity(i);


    }

    private void initialize() {
        activityMainBinding.tvshowsrv.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);
         adapter = new TVShowsAdapter(tvShowList,this);
         activityMainBinding.tvshowsrv.setAdapter(adapter);
         activityMainBinding.tvshowsrv.addOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
             public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                 super.onScrolled(recyclerView, dx, dy);
                 if(!activityMainBinding.tvshowsrv.canScrollVertically(1)){
                     if(currentpage<totalPages){
                         currentpage+=1;
                         getMostPopularTVShows();
                     }
                 }
             }
         });
         getMostPopularTVShows();


    }

    private void getMostPopularTVShows() {
          ToggleLoading();
        viewModel.getMostPopularTvShows(currentpage).observe(this, TvShowsResponse-> {
            ToggleLoading();

            if(TvShowsResponse!=null){
                totalPages = TvShowsResponse.getTotalpages();
                if(TvShowsResponse.getTvShowList()!=null){
                    int oldcount= tvShowList.size();
                    tvShowList.addAll(TvShowsResponse.getTvShowList());
                    adapter.notifyItemRangeInserted(oldcount,tvShowList.size());
                }
            }

        });
    }

    public void ToggleLoading(){
        if(currentpage==1){
            if(activityMainBinding.getIsLoading()){
                   activityMainBinding.setIsLoading(false);
            }
            else{
                activityMainBinding.setIsLoading(true);
            }
        }
        else{
            if(activityMainBinding.getIsLoadingMore()){
                activityMainBinding.setIsLoadingMore(false);
            }
            else{
                activityMainBinding.setIsLoadingMore(true);
            }
        }

    }

}