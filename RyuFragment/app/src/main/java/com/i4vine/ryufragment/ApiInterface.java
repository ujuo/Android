package com.i4vine.ryufragment;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/data/2.5/weather")
    Call<WeatherDay> getToday(@Query("appid") String appid, @Query("lat") double lat, @Query("lon") double lon);
    @GET("/data/2.5/forecast")
    Call<WeatherForecast> getForecast(@Query("appid") String appid, @Query("lat") double lat, @Query("lon") double lon, @Query("units") String unit);
}
