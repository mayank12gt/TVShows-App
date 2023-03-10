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

import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.viewholder> {

    List<TvShow> tvShowList;
    Context context;
    TvshowitemContainerBinding tvshowitemContainerBinding;

    public TVShowsAdapter(List<TvShow> tvShowList, Context context) {
        this.tvShowList = tvShowList;
        this.context = context;
    }

    @NonNull
    @Override
    public TVShowsAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        tvshowitemContainerBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.tvshowitem_container,
                parent,false);
        return new viewholder(tvshowitemContainerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowsAdapter.viewholder holder, int position) {
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
            tvshowitemContainerBinding.executePendingBindings();
        }

    }
}
