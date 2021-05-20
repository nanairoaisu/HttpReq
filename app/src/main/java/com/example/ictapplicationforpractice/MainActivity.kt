package com.example.ictapplicationforpractice

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener{
    var cityId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lvCity = findViewById<ListView>(R.id.lvCity)
        val cityList: MutableList<MutableMap<String, String>> = mutableListOf()
        var city = mutableMapOf("name" to "函館", "id" to "017010")
        cityList.add(city)
        city = mutableMapOf("name" to "仙台", "id" to "040010")
        cityList.add(city)
        city = mutableMapOf("name" to "東京", "id" to "130010")
        cityList.add(city)
        city = mutableMapOf("name" to "奈良", "id" to "290010")
        cityList.add(city)
        val from = arrayOf("name")
        val to = intArrayOf(android.R.id.text1)
        val adapter = SimpleAdapter(
            this,
            cityList,
            android.R.layout.simple_expandable_list_item_1,
            from,
            to
        )
        lvCity.adapter = adapter
        lvCity.onItemClickListener = this

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = parent?.getItemAtPosition(position) as Map<String, String>
        val cityName = item["name"]
        cityId = item["id"].toString()
        val tvWcity = findViewById<TextView>(R.id.tvWcity)
        tvWcity.text = cityName + "の天気："
        onParallelGetButtonClick()
    }

    fun onParallelGetButtonClick() =
    CoroutineScope(Dispatchers.Default).launch {
        val service = HttpClient().createService()
        val response = service.fetchCityWeather(cityId).execute()

        withContext(Dispatchers.Main) {
                response.body()?.let{
                    val tvWinfo = findViewById<TextView>(R.id.tvWinfo)
                    val tvWdesc = findViewById<TextView>(R.id.tvWdesc)
                    tvWinfo.text = it.forecasts[0].telop
                    tvWdesc.text = it.description["headlineText"].toString()
                }
            }
        }
}

