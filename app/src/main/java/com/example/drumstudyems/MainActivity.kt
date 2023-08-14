package com.example.drumstudyems

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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