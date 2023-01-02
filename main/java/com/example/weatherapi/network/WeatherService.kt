package com.example.weatherapi.network

import android.telecom.Call
import retrofit.Call
import retrofit.http.GET
import retrofit.http.Query

import com.example.weatherapi.models.WeatherResponse
interface WeatherService
{
    @GET("2.5/weather")
    fun getWeather(
        @Query("lat" lat: Double,
        @Query("lon" lon:Double,
            @Query("units") units: String?,
            @Query("appid") appid: String?
    ): Call<WeatherResponse>
}