package com.example.MollyMoranFYP.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("ALL")
public class ApiClient {
    //code here adapted from NewsApp by Satsih7897 on github url:https://github.com/Satish7897/NewsApp
    private static  final String BASE_URL="https://newsapi.org/v2/";
    private static com.example.MollyMoranFYP.Utils.ApiClient apiClient;
    private    static Retrofit retrofit;
    private ApiClient()
    {
       retrofit=new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized com.example.MollyMoranFYP.Utils.ApiClient getInstance()
    {
        if (apiClient==null)
        {
            apiClient=new com.example.MollyMoranFYP.Utils.ApiClient();
        }
        return apiClient;
    }
   public ApiInterface getApi()
   {
       return retrofit.create(ApiInterface.class);
   }
}
