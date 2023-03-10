package com.example.tvshows;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.adapters.TVShowsAdapter;
import com.example.tvshows.databinding.ActivitySearchBinding;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.viewmodel.SearchTVShowsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {


    ActivitySearchBinding activitySearchBinding;

    SearchTVShowsViewModel searchTVShowsViewModel;
    TVShowsAdapter tvShowsAdapter;
    List<TvShow> tvShowList;
    int totalPages;
    int currentpage =1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activitySearchBinding= DataBindingUtil.setContentView(this, R.layout.activity_search);
           activitySearchBinding.Searchtvshowbackbutton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   onBackPressed();
               }
           });

         initialize();

    }

    private void initialize() {
     searchTVShowsViewModel = new ViewModelProvider(this).get(SearchTVShowsViewModel.class);
     tvShowList = new ArrayList<>();

        tvShowsAdapter = new TVShowsAdapter(tvShowList,this);
        activitySearchBinding.searchtvshowsrv.setHasFixedSize(true);
        activitySearchBinding.searchtvshowsrv.setAdapter(tvShowsAdapter);

     getQuery();




     activitySearchBinding.searchtvshowsrv.addOnScrollListener(new RecyclerView.OnScrollListener() {
         @Override
         public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

             super.onScrolled(recyclerView, dx, dy);

             if(!activitySearchBinding.searchtvshowsrv.canScrollVertically(1)) {
                 if (!activitySearchBinding.searchview.getQuery().toString().isEmpty()){

                 if (currentpage < totalPages) {
                     currentpage += 1;

                     Toast.makeText(SearchActivity.this, activitySearchBinding.searchview.getQuery().toString(), Toast.LENGTH_SHORT).show();
                 }
             }
             }
         }
     });




    }

    private void getQuery() {


        activitySearchBinding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.trim().isEmpty()){
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    currentpage=1;
                                    totalPages=1;
                                    searchTVShow(newText,false);
                                }
                            });
                        }
                    },800);
                }
                else{
                    Toast.makeText(SearchActivity.this, "empty", Toast.LENGTH_SHORT).show();
                   // activitySearchBinding.searchtvshowsrv.setAdapter(null);
                    tvShowList.clear();

                    tvShowsAdapter.notifyDataSetChanged();
                }

                return true;
            }
        });



    }


    private void searchTVShow(String query,boolean onScroll) {
        ToggleLoading();
        searchTVShowsViewModel.searchTVShow(query,  currentpage).observe(this, TvShowsResponse-> {
            ToggleLoading();

            if(TvShowsResponse!=null){
                totalPages = TvShowsResponse.getTotalpages();
                if(TvShowsResponse.getTvShowList()!=null) {

                    if (onScroll == false) {

                        tvShowList.clear();
                        tvShowList.addAll(TvShowsResponse.getTvShowList());
                        tvShowsAdapter.notifyDataSetChanged();
                    }
                    else{

                        int oldcount= tvShowList.size();
                        tvShowList.addAll(TvShowsResponse.getTvShowList());
                        tvShowsAdapter.notifyItemRangeInserted(oldcount,tvShowList.size());
                    }
                }
            }

        });
    }

    public void ToggleLoading(){
        if(currentpage==1){
            if(activitySearchBinding.getIsLoading()){
                activitySearchBinding.setIsLoading(false);
            }
            else{
                activitySearchBinding.setIsLoading(true);
            }
        }
        else{
            if(activitySearchBinding.getIsLoadingMore()){
                activitySearchBinding.setIsLoadingMore(false);
            }
            else{
                activitySearchBinding.setIsLoadingMore(true);
            }
        }

    }

}