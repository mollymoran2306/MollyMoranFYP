package com.example.MollyMoranFYP.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.MollyMoranFYP.Adapters.OnboardingSlideViewPagerAdapter;
import com.example.MollyMoranFYP.R;

public class OnboardingSlideActivity extends AppCompatActivity {

    public static ViewPager viewPager;
    OnboardingSlideViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);
        getSupportActionBar().hide();

        viewPager=findViewById(R.id.viewpager);
        adapter=new OnboardingSlideViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        //uncomment!!
//        if (isOpenAlready())
//        {
//            Intent intent=new Intent(OnboardingSlideActivity.this,LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
//        else
//        {
            SharedPreferences.Editor editor=getSharedPreferences("slide",MODE_PRIVATE).edit();
            editor.putBoolean("slide",true);
            editor.commit();
        }

   // }

    private boolean isOpenAlready() {

        SharedPreferences sharedPreferences=getSharedPreferences("slide",MODE_PRIVATE);
        boolean result=sharedPreferences.getBoolean("slide",false);
        return result;

    }
}