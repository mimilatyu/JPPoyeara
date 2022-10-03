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
import com.example.poyearajpc.screens.MainCard
import com.example.poyearajpc.screens.TabLayout
import com.example.poyearajpc.ui.theme.PoYearAjpcTheme
import org.json.JSONObject



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoYearAjpcTheme {
                getData("London", this)
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
                    MainCard()
                    TabLayout()
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, context: Context) {
    val state = remember{
        mutableStateOf("Unknown")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxHeight(0.5f)
            .fillMaxWidth(),
             contentAlignment =  Alignment.Center){
            Text(text ="Temp in $name  ${state.value}")
        }
        Box(modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter){
            Button(onClick = {
                getResult(name, state, context )

            }, modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()) {
                Text(text ="Refresh")
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

private fun getData(city: String, context: Context) {
    val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
            API_KEY +
            "&q=" +
            city +
            "&days=1&aqi=no&alerts=no"

    val queue = Volley.newRequestQueue(context)
    val sRequest = StringRequest(
        com.android.volley.Request.Method.GET,
        url,
        {
        response ->
            Log.d("MyLog", "Somethin' ok, my bro: $response ")
        },
        {
            Log.d("MyLog", "Somethin' goes wrong, my bro: $it ")
        }
    )
    queue.add(sRequest)
}