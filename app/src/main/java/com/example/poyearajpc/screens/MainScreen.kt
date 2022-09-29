package com.example.poyearajpc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.poyearajpc.R
import com.example.poyearajpc.ui.theme.Pink1488

@Preview(showBackground = true)
@Composable
fun MainScreen() {
    //фон
    Image(
        painter = painterResource(id = R.drawable.pepe),
        contentDescription = "backgroundPepe",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.7f),
        contentScale = ContentScale.FillBounds
    )
    //основной контейнер
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Pink1488,
            elevation = 0.dp,
            shape = RoundedCornerShape(10.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = "20 Jun 2022 13:00",
                        style = TextStyle(fontSize = 15.sp),
                        color = Color.White
                    )
                    AsyncImage(
                        model = "https://cdn.weatherapi.com/weather/64x64/day/116.png",
                        contentDescription = "im2",
                        modifier = Modifier
                            .padding(top = 3.dp, end = 8.dp)
                            .size(35.dp)
                    )
                }
                Text(
                    text = "Madrid",
                    style = TextStyle(fontSize = 24.sp),
                    color = Color.White
                )
                Text(
                    text = "16°C",
                    style = TextStyle(fontSize = 65.sp),
                    color = Color.White
                )
                Text(
                    text = "Sunny",
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {

                    }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = "im3",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "23°C/32°C",
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )

                    IconButton(onClick = {

                    }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_sync_24),
                            contentDescription = "im4",
                            tint = Color.White
                        )
                    }
                }


            }
        }
    }
}