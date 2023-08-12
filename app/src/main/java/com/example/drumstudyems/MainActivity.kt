package com.example.drumstudyems

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.drumstudyems.ui.theme.DrumStudyEMSTheme
import com.example.drumstudyems.view.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrumStudyEMSTheme {
                AppNavigation()
            }
        }
    }
}