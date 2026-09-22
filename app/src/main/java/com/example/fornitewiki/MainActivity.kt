package com.example.fornitewiki

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fornitewiki.ui.home.HomeScreen
import com.example.fornitewiki.ui.theme.ForniteWikiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ForniteWikiTheme {
                HomeScreen()
            }
        }
    }
}
