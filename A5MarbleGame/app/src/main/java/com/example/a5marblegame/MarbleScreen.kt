package com.example.a5marblegame

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

val MARBLE_SIZE = 40.dp

// draws whatever the ViewModel's state says.
@Composable
fun MarbleScreen(viewModel: MarbleViewModel, sensorLabel: String) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()   // keep the marble out from under the status/nav bars
    ) {
        val maxX = (maxWidth - MARBLE_SIZE).value
        val maxY = (maxHeight - MARBLE_SIZE).value

        LaunchedEffect(maxX, maxY) {
            viewModel.setBounds(maxX, maxY)
        }

        Text(
            text = "Sensor: $sensorLabel",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(8.dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        // The marble. offset comes before size/background.
        Box(
            modifier = Modifier
                .offset(state.x.dp, state.y.dp)
                .size(MARBLE_SIZE)
                .background(Color.Magenta, CircleShape)
        )
    }
}