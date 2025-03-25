package com.example.rpz_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.rpz_lab3.ui.theme.RPZ_lab3Theme
import java.io.File

class ShowResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RPZ_lab3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DisplayAllResults()
                }
            }
        }
    }
}

@Composable
fun DisplayAllResults() {
    val context = LocalContext.current
    val fileResults = remember {
        val filename = "user_results.txt"
        val file = File(context.filesDir, filename)
        if (file.length() > 0 && file.exists()){
            file.readText()
        } else {
            "Попередні результати відсутні :("
        }
    }

    Column(modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)) {
        Text(
            text = fileResults,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(15.dp)
        )
        Button(
            onClick = { (context as ComponentActivity).finish() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) { Text ("Назад") }
    }
}