package com.example.MollyMoranFYP.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MollyMoranFYP.Adapters.NewsAdapter;
import com.example.MollyMoranFYP.R;
import com.example.MollyMoranFYP.Utils.ApiClient;
import com.example.MollyMoranFYP.Models.Articles;
import com.example.MollyMoranFYP.Models.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {
RecyclerView recyclerView;
NewsAdapter adapter;
final String API_KEY="6e8b6d9497e644da8c68762700ca8c1a";
Button button;
ImageButton floatingActionButton;
private static final String TAG = "*NewsActivity*";

List<Articles> articles=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main2);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        button=findViewById(R.id.refreshButton);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final String country="ie";
        Log.d(TAG, "country is " + country);
//    floatingActionButton=(ImageButton)findViewById(R.id.floating);
//    floatingActionButton.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent=new Intent(NewsActivity.this, NewsIntro.class);
//            startActivity(intent);
//        }
//    });

     /*	Code	below	is	based	on	NewsApp
                    by Satish7897 url:https://github.com/Satish7897/NewsApp
                     */

        retrieveJson(country,API_KEY);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               retrieveJson(country,API_KEY);
           }
       });
    }
    public  void retrieveJson(String country, String apiKey)
    {
        Call<Headlines> call= ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles()!=null){
                    articles.clear();
                    articles=response.body().getArticles();

                    adapter =new NewsAdapter(NewsActivity.this, articles);
                      recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {

                Toast.makeText(NewsActivity.this,"There is An Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //END
    public String getCountry()
    {
        Locale locale= Locale.getDefault();
        String country=locale.getCountry();
        return country.toLowerCase();


    }
}
