package view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


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


//@Preview(showBackground = true)
//@Composable
//fun ScreenStartPreview() {
//    DrumStudyEMSTheme {
//        ScreenStartRender({})
//    }
//}