package com.example.tvshows.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("episode")
    @Expose
    private String episode;

    @SerializedName("season")
    @Expose
    private String season;

    @SerializedName("air_date")
    @Expose
    private String airDate;

    public String getName() {
        return name;
    }

    public String getEpisode() {
        return episode;
    }

    public String getSeason() {
        return season;
    }

    public String getAirDate() {
        return airDate;
    }
}

