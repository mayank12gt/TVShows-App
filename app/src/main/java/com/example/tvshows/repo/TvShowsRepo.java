package com.example.tvshows.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tvshows.models.TVShowDetailsResponse;
import com.example.tvshows.models.TvShowsResponse;
import com.example.tvshows.network.ApiClient;
import com.example.tvshows.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowsRepo {

private ApiService apiService;

public TvShowsRepo(){
    apiService= ApiClient.getRetrofit().create(ApiService.class);
}


  public LiveData<TvShowsResponse> getMostPopularTvShows(int page){

    MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();
     apiService.getMostPopularTvShows(page).enqueue(new Callback<TvShowsResponse>() {
         @Override
         public void onResponse(Call<TvShowsResponse> call, Response<TvShowsResponse> response) {
             data.setValue(response.body());
         }

         @Override
         public void onFailure(Call<TvShowsResponse> call, Throwable t) {
             data.setValue(null);
         }
     });
    return data;
  }

  public LiveData<TVShowDetailsResponse> getTVShowDetails(String id) {
      MutableLiveData<TVShowDetailsResponse> data = new MutableLiveData<>();
       apiService.getTVShowDetails(id).enqueue(new Callback<TVShowDetailsResponse>() {
           @Override
           public void onResponse(Call<TVShowDetailsResponse> call, Response<TVShowDetailsResponse> response) {
               data.setValue(response.body());

           }

           @Override
           public void onFailure(Call<TVShowDetailsResponse> call, Throwable t) {
               data.setValue(null);
           }
       });
  return data;
}

    public LiveData<TvShowsResponse> searchTvShow(String query,int page){

        MutableLiveData<TvShowsResponse> data = new MutableLiveData<>();
        apiService.searchTVShow(query, page).enqueue(new Callback<TvShowsResponse>() {
            @Override
            public void onResponse(Call<TvShowsResponse> call, Response<TvShowsResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TvShowsResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
