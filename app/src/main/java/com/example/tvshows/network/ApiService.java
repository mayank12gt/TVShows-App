package com.example.tvshows.network;

import com.example.tvshows.models.TVShowDetailsResponse;
import com.example.tvshows.models.TvShowsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("most-popular")
    Call<TvShowsResponse> getMostPopularTvShows(@Query("page") int page);

    @GET("show-details")
    Call<TVShowDetailsResponse> getTVShowDetails(@Query("q") String id);
    @GET("search")
    Call<TvShowsResponse> searchTVShow(@Query("q") String query, @Query("page") int page);
}
