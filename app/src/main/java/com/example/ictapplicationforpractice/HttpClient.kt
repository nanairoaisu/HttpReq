package com.example.ictapplicationforpractice

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient {
    fun createService():HttpService {
        val gson = GsonBuilder()
            .serializeNulls()
            .create()

        val client = httpGet()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://weather.tsukumijima.net/api/forecast/city/")
            .client(client.build())
            .build()

        val service = retrofit.create(HttpService::class.java)
        return service
    }

    private fun httpGet() : OkHttpClient.Builder{
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .method(original.method, original.body)
                .build()

            val response = chain.proceed(request)
            return@Interceptor response
        })
            .readTimeout(30, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient
    }


}