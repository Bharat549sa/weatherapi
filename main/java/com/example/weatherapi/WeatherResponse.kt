package com.example.weatherapi
import java.io.Serilizable

data class WeatherResponse {
val coord:Coord,
        val weather: List<Weather>,
        val base: String,
                val main: Main,
                     val visiblity: Int,
                     val wind: Wind,
                     val clouds: Clouds,
                     val dt:Int,
                     val sys:Sys,
                     val id:Int,
    ):Serializable


}