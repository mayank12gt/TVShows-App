package com.example.tvshows.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.R;
import com.example.tvshows.databinding.EpisodeItemBinding;
import com.example.tvshows.models.Episode;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.viewholder> {

   List<Episode> episodesList;
   Context context;
   EpisodeItemBinding episodeItemBinding;

    public EpisodesAdapter(List<Episode> episodesList, Context context) {
        this.episodesList = episodesList;
        this.context = context;
    }

    @NonNull
    @Override
    public EpisodesAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        episodeItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.episode_item,parent,false);
        return new viewholder(episodeItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodesAdapter.viewholder holder, int position) {
     Episode episode = episodesList.get(position);

     holder.bindEpisode(episode);
    }

    @Override
    public int getItemCount() {
        return episodesList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

        EpisodeItemBinding episodeItemBinding;

        public viewholder( EpisodeItemBinding episodeItemBinding) {
            super(episodeItemBinding.getRoot());
            this.episodeItemBinding = episodeItemBinding;
        }

        public void bindEpisode(Episode episode){
            String title="S";

            String season= episode.getSeason();
            if(season.length()==1){
                season = "0".concat(season);
            }

            String episodeno= episode.getEpisode();

            if(episodeno.length()==1){
                episodeno = "0".concat(episodeno);
            }

            title = title.concat(season).concat("E").concat(episodeno);
            episodeItemBinding.setTitle(title);
            episodeItemBinding.setAirdate(episode.getAirDate());
            episodeItemBinding.setName(episode.getName());

        }
    }
}
