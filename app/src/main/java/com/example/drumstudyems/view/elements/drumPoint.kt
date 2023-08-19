package com.example.drumstudyems.view.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.drumstudyems.model.LeftRight

@Composable
fun drumPoint (optimalHit : Dp, tolerance : Dp, offsetY : Dp, offsetX : Dp, side : LeftRight){
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
    }
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
            .height(optimalHit + tolerance*2)
            .width(5.dp),
        color = Color.Red,

    )
}

//@Preview(showBackground = true)
//@Composable
//fun drumPointPreview() {
//    DrumStudyEMSTheme {
//        drumPoint(20.dp,20.dp,0.dp)
//    }
//}