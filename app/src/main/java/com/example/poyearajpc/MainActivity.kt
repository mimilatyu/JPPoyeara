package com.example.poyearajpc

import android.app.DownloadManager.Request
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.poyearajpc.data.WeatherModel
import com.example.poyearajpc.screens.MainCard
import com.example.poyearajpc.screens.TabLayout
import com.example.poyearajpc.ui.theme.PoYearAjpcTheme
import org.json.JSONObject



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoYearAjpcTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherModel>())
                }

                val currentDay = remember {
                    mutableStateOf(WeatherModel(
                        "",
                        "",
                        "10.0",
                        "",
                        "",
                        "10.0",
                        "10.0",
                        "",
                    ))
                }

                getData("London", this, daysList, currentDay)
                //фон
                Image(
                    painter = painterResource(id = R.drawable.pepe),
                    contentDescription = "backgroundPepe",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f),
                    contentScale = ContentScale.FillBounds
                )
                Column {
                    MainCard(currentDay)
                    TabLayout(daysList)
                }

            }
        }
    }
}

private fun getResult(city: String, state: MutableState<String>, context: Context){
    val url = "https://api.weatherapi.com/v1/current.json" +
            "?key=$API_KEY&q" +
            "=$city" +
            "&aqi=no"
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url,
        {
            response ->
            val obj = JSONObject(response)
            state.value = obj.getJSONObject("current").getString("temp_c") +" °C"

        },
        {
            error -> Log.d("MyLog", "Error $error")
        }
    )
    queue.add(stringRequest)
}

private fun getData(city: String, context: Context, daysList: MutableState<List<WeatherModel>>,
                    currentDay: MutableState<WeatherModel>) {
    val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
            API_KEY +
            "&q=" +
            city +
            "&days=3&aqi=no&alerts=no"

    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url,
        {
        response ->
            val list = getWeatherByDays(response)
            currentDay.value = list[0]
            daysList.value = list
        },
        {
            Log.d("MyLog", "Somethin' goes wrong, my bro: $it ")
        }
    )
    queue.add(sRequest)
}

private fun getWeatherByDays(response: String) : List<WeatherModel>{
    if(response.isEmpty()) return listOf()
    val list = ArrayList<WeatherModel>()
    val mainObject = JSONObject(response)
    val city = mainObject.getJSONObject("location").getString("name")
    val days = mainObject.getJSONObject("forecast")
        .getJSONArray("forecastday")

    for (i in 0 until days.length()){
        val item = days[i] as JSONObject
        list.add(
            WeatherModel(
                city,
                item.getString("date"),
                "",
                item.getJSONObject("day").getJSONObject("condition").getString("text"),
                item.getJSONObject("day").getJSONObject("condition").getString("icon"),
                item.getJSONObject("day").getString("maxtemp_c"),
                item.getJSONObject("day").getString("mintemp_c"),
                item.getJSONArray("hour").toString()

            )
        )
    }
    list[0] = list[0].copy(
        time = mainObject.getJSONObject("current").getString("last_updated"),
        currentTemp = mainObject.getJSONObject("current").getString("temp_c")+"°C"
    )
    return list
}