package com.kat4x.coolcomposeclock

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

data class Time(
    val hour: Int,
    val minutes : Int,
    val seconds: Int
)

@Composable
fun Clock(time: Time) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val padding = Modifier.padding(horizontal = 4.dp)

        NumberColumn(range = 0..2, current = time.hour / 10, padding)
        NumberColumn(range = 0..9, current = time.hour % 10, padding)
        Spacer(modifier = Modifier.size(16.dp))
        
        NumberColumn(range = 0..5, current = time.minutes / 10, padding)
        NumberColumn(range = 0..9, current = time.minutes % 10, padding)
        Spacer(modifier = Modifier.size(16.dp))

        NumberColumn(range = 0..5, current = time.seconds / 10, padding)
        NumberColumn(range = 0..9, current = time.seconds % 10, padding)
    }
}
