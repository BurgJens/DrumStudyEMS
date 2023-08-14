package com.example.drumstudyems.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.drumstudyems.ui.theme.DrumStudyEMSTheme

@Composable
fun ScreenStartHandler(
    navigateFirst : () -> Unit
){
    ScreenStartRender(
        navigateStart = navigateFirst
    )
}

@Composable
fun ScreenStartRender(
    navigateStart : () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = navigateStart
        ){
            Text(text = "Start")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ScreenStartPreview() {
    DrumStudyEMSTheme {
        ScreenStartRender({})
    }
}