package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import appDebugMode
import appTimeFrame
import model.LeftRight
import model.RythmManagerData
import model.TimeData
import kotlinx.coroutines.Dispatchers
import viewmodel.DrumStudyViewModel
import appWindowSize


@Composable
fun ScreenDrumHeroData(
    drumStudyViewModel: DrumStudyViewModel,
    navigateBack : () -> Unit,
){
    val rythmManagerData by drumStudyViewModel.getRythmManagerData()
        .collectAsState(
            initial = RythmManagerData(
                timeData = TimeData(0L,0L),
                drumNotes = listOf(),
                drumHits = listOf(),
                metronome = listOf()
            ), Dispatchers.Main
        )

    ScreenDrumHero(
        rythmManagerData = rythmManagerData,
        timeFrame = appTimeFrame,
        optimalHitTimeFrame = 200L,
        {drumStudyViewModel.debugButtonInpug(rythmManagerData.timeData.currentTime)},
        navigateBack
    )
}

@Composable
fun ScreenDrumHero(
    rythmManagerData: RythmManagerData,
    timeFrame: Long,
    optimalHitTimeFrame : Long,
    debugButtonInput : () -> Unit,
    navigateBack : () -> Unit,
){

    val screenSize = appWindowSize
    val screenHeight = screenSize.height
    val screenWidth = screenSize.width

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    )
    {

        RythmBox(
            Color.White,
            screenWidth,
            screenHeight,
            rythmManagerData,
            timeFrame,
            optimalHitTimeFrame
        )
        if (appDebugMode){
            Text(
                text = "${rythmManagerData.timeData.currentTime}",
                modifier = Modifier
                    .offset(y = -screenHeight / 3),
                color = Color.LightGray
            )
            val buttonHeight = screenHeight/20
            OutlinedButton(
                onClick = debugButtonInput,
                modifier = Modifier
                    .width(screenWidth)
                    .offset(y = -screenHeight / 3 + buttonHeight/2)
                    .height(buttonHeight)
                    .alpha(0.5f),
            ){}
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {navigateBack()}
            ){ Text("Stop") }
        }

    }

}

@Composable
fun RythmBox(
    color : Color,
    screenWidth : Dp,
    screenHeight : Dp,
    rythmManagerData : RythmManagerData,
    timeFrame: Long,
    optimalHitTimeFrame : Long
    ){
    val upperEdge = rythmManagerData.timeData.currentTime + (timeFrame/3*2)
    val lowerEdge = rythmManagerData.timeData.currentTime - (timeFrame/3)

    val optimalHitSize = screenHeight.times(optimalHitTimeFrame.toFloat() / timeFrame.toFloat())

    Box (
        modifier = Modifier
            .background(color)
            .width(screenWidth/3)
            .height(screenHeight),
        contentAlignment = Alignment.TopCenter
    ){
        Card(
            modifier = Modifier
                .fillMaxSize(),
            border = BorderStroke(2.dp,Color.Black),
            backgroundColor = Color.White
        ) {

        }
        CurrentTimeLine(rythmManagerData)
        for(each in rythmManagerData.drumNotes){
            val t = (each.playTime - upperEdge).toFloat() / (lowerEdge - upperEdge).toFloat()
            val posY = screenHeight.times(t)
            MusicNote(
                //timeFrame = each.timeFrame.toInt().dp,
                timeFrame = screenHeight.times(each.timeFrame.toInt() / timeFrame.toFloat()),
                offsetX = 150.dp,
                offsetY = posY,
                side = each.side,
                hitTime = each.playTime)

        }
        for(each in rythmManagerData.drumHits){
            val t = (each.hitTime - upperEdge).toFloat() / (lowerEdge - upperEdge).toFloat()
            val posY = screenHeight.times(t)
            DrumHit(
                offsetX = 150.dp,
                offsetY = posY,
                side = each.side
            )
        }
        Metronome(rythmManagerData)
    }

}

@Composable
fun Metronome(rythmManagerData : RythmManagerData){
    val upperEdge = rythmManagerData.timeData.currentTime + (appTimeFrame/3*2)
    val lowerEdge = rythmManagerData.timeData.currentTime - (appTimeFrame/3)

    for (each in rythmManagerData.metronome) {

        val t = (each - upperEdge).toFloat() / (lowerEdge - upperEdge).toFloat()
        val posY = appWindowSize.height.times(t)

        Divider(
            modifier = Modifier
                .height(2.dp)
                .offset(y = posY)
                .alpha(0.5f),
            color = Color.Blue,
        )
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


@Composable
fun MusicNote (
    timeFrame : Dp,
    offsetY : Dp,
    offsetX : Dp,
    side : LeftRight,
    hitTime : Long,
){
    val side = when(side){
        LeftRight.RIGHT -> 1
        LeftRight.LEFT -> -1
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.
        offset(y = offsetY- timeFrame/2, x = offsetX*side)
    ){
        DrumHitCircle(timeFrame)
        if (appDebugMode){
            Text(
                text = "$hitTime",
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun DrumHit(
    offsetY : Dp,
    offsetX : Dp,
    side : LeftRight,
){
    val side = when(side){
        LeftRight.RIGHT -> 1
        LeftRight.LEFT -> -1
    }
    Divider(
        modifier = Modifier
            .offset(y = offsetY, x = offsetX * side)
            .width(75.dp)
            .height(2.dp),
        color = Color.Black
    )

}

@Composable
fun DrumHitCircle (optimalHit: Dp){
    Canvas(
        modifier = Modifier
            .width(optimalHit)
            .height(optimalHit)
    ) {
        drawCircle(Color.Blue, radius = (optimalHit/2).toPx())
    }
}

@Composable
fun CurrentTimeLine(rythmManagerData : RythmManagerData){
    val upperEdge = rythmManagerData.timeData.currentTime + (appTimeFrame/3*2)
    val lowerEdge = rythmManagerData.timeData.currentTime - (appTimeFrame/3)

    val t = (rythmManagerData.timeData.currentTime - upperEdge).toFloat() / (lowerEdge - upperEdge).toFloat()
    val posY = appWindowSize.height.times(t)

    if (appDebugMode) ShowTimer(timeData = rythmManagerData.timeData, offsetY = posY)

    Divider(
        modifier = Modifier
            .height(2.dp)
            .offset(y = posY)
            .alpha(0.5f),
        color = Color.Red,
    )
}


//@Preview(showBackground = true)
//@Composable
//fun ScreenDrumHeroPreview() {
//    DrumStudyEMSTheme {
//        ScreenDrumHero(Pair(1L, 0L))
//    }
//}

