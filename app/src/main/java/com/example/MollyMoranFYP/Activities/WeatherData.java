package com.example.MollyMoranFYP.Activities;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherData {


 /*	Code	below	is	based	on	the YouTube video Weather app in android studio | Part -2 | Current Location
                    by Tech Projects url:https://www.youtube.com/watch?v=VHgM_MQBQPg&t=262s
                     */
    private String mTemperature,micon,mcity,mWeatherType, mHigh, mLow;
    private int mCondition;
    private static final String TAG = "*WeatherData*";

    public static WeatherData fromJson(JSONObject jsonObject)
    {

        try
        {
            Log.d(TAG, "jsonObject is " + jsonObject);

            WeatherData weatherD=new WeatherData();
            weatherD.mcity=jsonObject.getString("name");
            weatherD.mCondition=jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.mWeatherType=jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.micon=updateWeatherIcon(weatherD.mCondition);

            double tempResult=jsonObject.getJSONObject("main").getDouble("temp")-273.15;
            int roundedValue=(int)Math.rint(tempResult);
            weatherD.mTemperature=Integer.toString(roundedValue);

            double tempHigh=jsonObject.getJSONObject("main").getDouble("temp_max")-273.15;
            int roundedValue1=(int)Math.rint(tempResult);
            weatherD.mHigh=Integer.toString(roundedValue1);

            double tempLow=jsonObject.getJSONObject("main").getDouble("temp_min")-273.15;
            int roundedValue2=(int)Math.rint(tempResult);
            weatherD.mLow=Integer.toString(roundedValue2);

            return weatherD;
        }


        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }


    private static String updateWeatherIcon(int condition)
    {
        if(condition>=0 && condition<=300)
        {
            return "thunderstrom1";
        }
        else if(condition>=300 && condition<=500)
        {
            return "lightrain";
        }
        else if(condition>=500 && condition<=600)
        {
            return "shower";
        }
        else  if(condition>=600 && condition<=700)
        {
            return "snow2";
        }
        else if(condition>=701 && condition<=771)
        {
            return "fog";
        }

        else if(condition>=772 && condition<=800)
        {
            return "overcast";
        }
        else if(condition==800)
        {
            return "sunny";
        }
        else if(condition>=801 && condition<=804)
        {
            return "cloudy";
        }
        else  if(condition>=900 && condition<=902)
        {
            return "thunderstrom1";
        }
        if(condition==903)
        {
            return "snow1";
        }
        if(condition==904)
        {
            return "sunny";
        }
        if(condition>=905 && condition<=1000)
        {
            return "thunderstrom2";
        }

        return "dunno";


    }
    //END

    public String getmTemperature() {
        return mTemperature+"°C";
    }

    public String getMicon() {
        return micon;
    }

    public String getMcity() {
        return mcity;
    }

    public String getmWeatherType() {
        return mWeatherType;
    }

    public String getmHigh() {
        return mHigh+"°C";
    }
    public String getmLow() {
        return mLow+"°C";
    }

}

