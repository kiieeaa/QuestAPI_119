package com.example.praktikum12

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.praktikum12.ui.theme.Praktikum12Theme
import com.example.praktikum12.uicontroller.DataSiswaApp // Pastikan import ini ada

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Praktikum12Theme {
                // Scaffold utama yang membungkus seluruh navigasi aplikasi
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Mengganti Greeting() dengan DataSiswaApp agar aplikasi berjalan
                    DataSiswaApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}