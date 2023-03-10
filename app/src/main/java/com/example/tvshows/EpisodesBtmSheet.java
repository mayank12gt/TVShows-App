package com.example.tvshows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvshows.adapters.EpisodesAdapter;
import com.example.tvshows.models.Episode;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;


public class EpisodesBtmSheet extends BottomSheetDialogFragment {

   List<Episode> episodeList;
   String name;

    public EpisodesBtmSheet(List<Episode> episodeList,String name) {
        this.episodeList = episodeList;
        this.name=name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.episodes_btm_sheet, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.EpisodesRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new EpisodesAdapter(episodeList,getContext()));

        ImageView close = v.findViewById(R.id.episodeClose);
        TextView title= v.findViewById(R.id.EpisodesTextTitle);

             title.setText(name+" | Episodes");

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;
    }
}