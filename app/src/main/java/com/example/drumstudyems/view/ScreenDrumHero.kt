package com.example.drumstudyems.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drumstudyems.model.DrumHit
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
        2000
    )
}

@Composable
fun ScreenDrumHero(
    rythmManagerData: RythmManagerData,
    timeFrame: Int
){
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val heightInMS = screenHeight / timeFrame

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    )
    {

        RythmBox(Color.LightGray, screenWidth, screenHeight, rythmManagerData)

        Divider(
            modifier = Modifier
                .height(10.dp)
                .offset(y = screenHeight / 3 * 2),
            color = Color.Cyan,
        )

        ShowTimer(rythmManagerData.timeData, screenHeight/100*95)
    }

}

@Composable
fun RythmBox(color : Color, width : Dp, height : Dp, rythmManagerData : RythmManagerData){
    Log.d("RythmBox", "${rythmManagerData.drumHits.size}")
    Box (
        modifier = Modifier
            .background(color)
            .width(width)
            .height(height),
        contentAlignment = Alignment.TopCenter
    )
    {
//        for(each in rythmManagerData.drumHits){
////            Log.d("RythmBox", "${each.hitTime}")
//            drumPoint(
//                optimalHit = 20.dp,
//                tolerance = 20.dp,
//                offsetY = (each.hitTime - rythmManagerData.timeData.currentTime).toInt().dp / 100)
//        }
        if(!rythmManagerData.drumHits.isEmpty()) {
            drumPoint(
                optimalHit = 20.dp,
                tolerance = 20.dp,
                offsetY = 500.dp,
                offsetX = width / 4,
                side = rythmManagerData.drumHits.last().side
            )
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

