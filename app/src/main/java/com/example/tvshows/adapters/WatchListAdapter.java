package com.example.tvshows.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.R;
import com.example.tvshows.TVShowDetails;
import com.example.tvshows.databinding.TvshowitemContainerBinding;
import com.example.tvshows.models.TvShow;
import com.example.tvshows.viewmodel.WatchListActivityViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.viewholder> {

    List<TvShow> tvShowList;
    Context context;
    TvshowitemContainerBinding tvshowitemContainerBinding;
    WatchListActivityViewModel watchListActivityViewModel;

    public WatchListAdapter(List<TvShow> tvShowList, Context context, WatchListActivityViewModel watchListActivityViewModel) {
        this.tvShowList = tvShowList;
        this.context = context;
        this.watchListActivityViewModel = watchListActivityViewModel;
    }

    @NonNull
    @Override
    public WatchListAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        tvshowitemContainerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.tvshowitem_container,
                parent,false);
        return new viewholder(tvshowitemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchListAdapter.viewholder holder, int position) {
      TvShow tvShow = tvShowList.get(position);
      holder.bindTVShow(tvShow);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context, TVShowDetails.class);
             intent.putExtra("tvShow",tvShow);
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        private TvshowitemContainerBinding tvshowitemContainerBinding;

        public viewholder(TvshowitemContainerBinding tvshowitemContainerBinding) {
            super(tvshowitemContainerBinding.getRoot());
            this.tvshowitemContainerBinding= tvshowitemContainerBinding;
        }
        public void bindTVShow(TvShow tvShow){
            tvshowitemContainerBinding.setTVShow(tvShow);
            tvshowitemContainerBinding.delete.setVisibility(View.VISIBLE);
            tvshowitemContainerBinding.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            watchListActivityViewModel.deleteTVShowfromWatchList(tvShow);

                        }
                    });

                }
            });
            tvshowitemContainerBinding.executePendingBindings();
        }

    }
}
