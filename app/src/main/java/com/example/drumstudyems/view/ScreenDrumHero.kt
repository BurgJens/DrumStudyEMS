package com.example.drumstudyems.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drumstudyems.model.RythmManagerData
import com.example.drumstudyems.model.TimeData
import com.example.drumstudyems.view.elements.drumPoint
import com.example.drumstudyems.viewmodel.DrumStudyViewModel
import kotlinx.coroutines.Dispatchers



@Composable
fun ScreenDrumHeroData(drumStudyViewModel: DrumStudyViewModel){

    val rythmManagerData by
    drumStudyViewModel.getRythmManagerData()
        .collectAsState(initial = RythmManagerData(
            timeData = TimeData(0L,0L),
            drumHits = listOf()
        ), Dispatchers.Main)

//    val test by
//    drumStudyViewModel.startTimer().collectAsState(initial = TimeData(0L,0L))

    ScreenDrumHero(
        rythmManagerData,
        5000L,
        true
    )
}

@Composable
fun ScreenDrumHero(
    rythmManagerData: RythmManagerData,
    timeFrame: Long,
    debug : Boolean
){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    )
    {

        RythmBox(Color.LightGray, screenWidth, screenHeight, rythmManagerData, timeFrame, debug)

        Divider(
            modifier = Modifier
                .height(10.dp)
                .offset(y = -screenHeight / 3),
            color = Color.Cyan,
        )
        if (debug){
            Text(
                text = "${rythmManagerData.timeData.currentTime}",
                modifier = Modifier.offset(y = -screenHeight / 3)
            )
        }


        ShowTimer(rythmManagerData.timeData, screenHeight/100*95)
    }

}

@Composable
fun RythmBox(
    color : Color,
    screenWidth : Dp,
    screenHeight : Dp,
    rythmManagerData : RythmManagerData,
    timeFrame: Long,
    debug: Boolean
    ){
    val upperEdge = rythmManagerData.timeData.currentTime + (timeFrame/3*2)
    val lowerEdge = rythmManagerData.timeData.currentTime - (timeFrame/3)

    Log.d("dingDong", "upperEdge $upperEdge")
    Log.d("dingDong", "lowerEdge $lowerEdge")
    Log.d("dingDong", "currentTime ${rythmManagerData.timeData.currentTime}")

//    Log.d("RythmBox", "${rythmManagerData.drumHits.size}")
    Box (
        modifier = Modifier
            .background(color)
            .width(screenWidth)
            .height(screenHeight),
        contentAlignment = Alignment.TopCenter
    )
    {
        for(each in rythmManagerData.drumHits){
            val t = (each.hitTime - upperEdge).toDouble() / (lowerEdge - upperEdge).toDouble()
//            val posY =
//                500.dp
//            Log.d("$each", "${t}")
            val posY = screenHeight.times(t.toFloat())
            drumPoint(
                optimalHit = 20.dp,
                tolerance = 20.dp,
                offsetX = screenWidth / 4,
                offsetY = posY,
                side = each.side,
                debug = debug,
                hitTime = each.hitTime)

        }
    }
}


@Composable
fun ShowTimer(timeData: TimeData, offsetY : Dp){
        Text(
            text = "${timeData.currentTime}  |  ${timeData.deltaTime}",
            modifier = Modifier.offset(y = offsetY),
            fontSize = 30.sp
        )
}


//@Preview(showBackground = true)
//@Composable
//fun ScreenDrumHeroPreview() {
//    DrumStudyEMSTheme {
//        ScreenDrumHero(Pair(1L, 0L))
//    }
//}

