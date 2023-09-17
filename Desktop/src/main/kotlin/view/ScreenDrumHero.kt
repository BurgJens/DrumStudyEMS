package view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
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
                drumHits = listOf(),
                input = listOf()
            ), Dispatchers.Main
        )

    ScreenDrumHero(
        rythmManagerData = rythmManagerData,
        timeFrame = appTimeFrame,
        optimalHitTimeFrame = 200L,
        hitTimeTolerance = 100L,
        {drumStudyViewModel.debugButtonInpug(rythmManagerData.timeData.currentTime)},
        navigateBack
    )
}

@Composable
fun ScreenDrumHero(
    rythmManagerData: RythmManagerData,
    timeFrame: Long,
    optimalHitTimeFrame : Long,
    hitTimeTolerance : Long,
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
            optimalHitTimeFrame,
            hitTimeTolerance)

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

        Button(
            onClick = {navigateBack()},
            modifier = Modifier.align(Alignment.BottomStart)
        ){}
    }

}

@Composable
fun RythmBox(
    color : Color,
    screenWidth : Dp,
    screenHeight : Dp,
    rythmManagerData : RythmManagerData,
    timeFrame: Long,
    optimalHitTimeFrame : Long,
    hitTimeTolerance : Long
    ){
    val upperEdge = rythmManagerData.timeData.currentTime + (timeFrame/3*2)
    val lowerEdge = rythmManagerData.timeData.currentTime - (timeFrame/3)

    val optimalHitSize = screenHeight.times(optimalHitTimeFrame.toFloat() / timeFrame.toFloat())
    val hitToleranceSize = screenHeight.times(hitTimeTolerance.toFloat() / timeFrame.toFloat())

    Box (
        modifier = Modifier
            .background(color)
            .width(screenWidth)
            .height(screenHeight),
        contentAlignment = Alignment.TopCenter
    )
    {
        for(each in rythmManagerData.drumHits){
            val t = (each.hitTime - upperEdge).toFloat() / (lowerEdge - upperEdge).toFloat()
            val posY = screenHeight.times(t)
            drumPoint(
                optimalHit = optimalHitSize,
                tolerance = hitToleranceSize,
                offsetX = screenWidth / 4,
                offsetY = posY,
                side = each.side,
                hitTime = each.hitTime)

        }
        for(each in rythmManagerData.input){
            val t = (each.second - upperEdge).toFloat() / (lowerEdge - upperEdge).toFloat()
            val posY = screenHeight.times(t)
            drumHit(
                offsetX = screenWidth / 4,
                offsetY = posY,
                side = each.first
            )
        }

        currentTimeLine(rythmManagerData)
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
fun drumPoint (
    optimalHit : Dp,
    tolerance : Dp,
    offsetY : Dp,
    offsetX : Dp,
    side : LeftRight,
    hitTime : Long
){
    val side = when(side){
        LeftRight.RIGHT -> 1
        LeftRight.LEFT -> -1
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.
        offset(y = offsetY- optimalHit/2 - tolerance, x = offsetX*side)
    ){
        toleranceLine(optimalHit, tolerance)
        drumCircle(optimalHit)
        if (appDebugMode){
            Text(
                text = "$hitTime",
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun drumHit(
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
            .height(5.dp),
        color = Color.Black
    )

}

@Composable
fun drumCircle (optimalHit: Dp){
    Canvas(
        modifier = Modifier
            .width(optimalHit)
            .height(optimalHit)
    ) {
        drawCircle(Color.Blue, radius = (optimalHit/2).toPx())
    }
}

@Composable
fun toleranceLine (optimalHit : Dp, tolerance: Dp){
    Divider(
        modifier = Modifier
            .height(optimalHit + tolerance * 2)
            .width(5.dp),
        color = Color.Red,

        )
}

@Composable
fun currentTimeLine(rythmManagerData : RythmManagerData){
    val upperEdge = rythmManagerData.timeData.currentTime + (appTimeFrame/3*2)
    val lowerEdge = rythmManagerData.timeData.currentTime - (appTimeFrame/3)

    val t = (rythmManagerData.timeData.currentTime - upperEdge).toFloat() / (lowerEdge - upperEdge).toFloat()
    val posY = appWindowSize.height.times(t)

    Divider(
        modifier = Modifier
            .height(5.dp)
            .offset(y = posY)
            .alpha(0.5f),
        color = Color.Black,
    )
}


//@Preview(showBackground = true)
//@Composable
//fun ScreenDrumHeroPreview() {
//    DrumStudyEMSTheme {
//        ScreenDrumHero(Pair(1L, 0L))
//    }
//}

