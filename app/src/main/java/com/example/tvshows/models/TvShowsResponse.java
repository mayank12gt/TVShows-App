package com.example.tvshows.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowsResponse {
    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("pages")
    @Expose
    private Integer totalpages;

    @SerializedName("tv_shows")
    @Expose
    private List<TvShow> tvShowList;

    public Integer getTotal() {
        return total;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getTotalpages() {
        return totalpages;
    }

    public List<TvShow> getTvShowList() {
        return tvShowList;
    }
}
