package com.example.pc.whatstheweather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherApi {
    @GET("data/2.5/weather?")
    Call<IncomingResult> getCurrentWeatherData(@Query("q") String d, @Query("appid") String id);

}

