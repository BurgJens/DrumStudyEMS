package com.example.drumstudyems.view.elements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.drumstudyems.ui.theme.DrumStudyEMSTheme

@Composable
fun fallingPoint (speed : Int){
    val pos = remember{mutableStateOf(0)}

    drumCircle(10.dp)
    
}

@Composable
fun drumCircle (size: Dp){
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .wrapContentSize(Alignment.Center)
    )
}