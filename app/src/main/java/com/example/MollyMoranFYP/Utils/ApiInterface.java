package com.example.MollyMoranFYP.Utils;

import com.example.MollyMoranFYP.Models.Headlines;
import com.example.MollyMoranFYP.Models.Headlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    //code here adapted from NewsApp by Satsih7897 on github url:https://github.com/Satish7897/NewsApp
    @GET("top-headlines")
    Call<Headlines> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
