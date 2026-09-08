package com.example.lecture4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text("Hello World")
        }
    }
}

@Composable
fun ConcatWords() {
    var firstBox by remember { mutableStateOf(value = "") }
    var secondBox by remember { mutableStateOf(value = "") }
    var result by remember { mutableStateOf(value = "") }

    Column {
        TextField(
            value = firstBox,
            onValueChange = { firstBox = it },
            label = { Text("Word one:") }
        )
        TextField(
            value = secondBox,
            onValueChange = { secondBox = it },
            label = { Text("Word two:") }
        )
        Button(onClick = { result = firstBox + secondBox }) {
            Text("Combine")
        }
        Text("Result: $result")
    }
}