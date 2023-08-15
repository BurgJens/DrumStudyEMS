package com.example.drumstudyems.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.drumstudyems.ui.theme.DrumStudyEMSTheme
import com.example.drumstudyems.view.elements.drumPoint
import com.example.drumstudyems.viewmodel.DrumStudyViewModel
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ScreenDrumHeroData(drumStudyViewModel: DrumStudyViewModel){

    val currentTime by drumStudyViewModel.getCurrentTime().observeAsState()

    ScreenDrumHero(
        currentTime
    )
}

@Composable
fun ScreenDrumHero(
    currentTime : Long?
){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val leftPosX = screenWidth/3
    val rightPosX = screenWidth/3*2

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    )
    {
        Row {
            RythmBox(Color.DarkGray, screenWidth/2, screenHeight)
            RythmBox(Color.LightGray, screenWidth/2, screenHeight)
        }
        Divider(
            modifier = Modifier
                .height(10.dp)
                .offset(y = screenHeight / 3 * 2),
            color = Color.Cyan,
        )

        showTimer(currentTime)
    }

}

@Composable
fun RythmBox(color : Color, width : Dp, height : Dp){
    Box (
        modifier = Modifier
            .background(color)
            .width(width)
            .height(height),
        contentAlignment = Alignment.TopCenter
    )
    {
        drumPoint(20.dp,20.dp, 0.dp)
        drumPoint(20.dp,20.dp, 20.dp)
        drumPoint(20.dp,20.dp, 40.dp)
    }
}

@Composable
fun showTimer(currentTime : Long?){
    val renderTime = currentTime ?: 0L
    Text(
        modifier = Modifier
            ,
        text = renderTime.toString()
    )
}


@Preview(showBackground = true)
@Composable
fun ScreenDrumHeroPreview() {
    DrumStudyEMSTheme {
        ScreenDrumHero(1L)
    }
}

