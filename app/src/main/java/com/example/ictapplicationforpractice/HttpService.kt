package com.example.ictapplicationforpractice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface HttpService{
    @GET("{id}")
    fun fetchCityWeather(@Path("id") id: String): Call<Data>

}
