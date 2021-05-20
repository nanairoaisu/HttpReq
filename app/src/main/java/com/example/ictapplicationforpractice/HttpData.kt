package com.example.ictapplicationforpractice

data class Data(
    val description: Map<String, String> ,
    val forecasts: List<Forecast>
)

data class Forecast(
    val telop: String
)