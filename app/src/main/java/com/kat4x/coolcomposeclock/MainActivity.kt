package com.kat4x.coolcomposeclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kat4x.coolcomposeclock.ui.theme.CoolComposeClockTheme
import kotlinx.coroutines.delay
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoolComposeClockTheme {
                ClockPreview()

                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                }
            }
        }
    }
}


@Composable
fun Number(value: Int, active: Boolean, modifier: Modifier = Modifier) {
    val backgroundColor by animateColorAsState(
        targetValue = if (active)
            MaterialTheme.colors.primary
        else
            MaterialTheme.colors.primaryVariant
    )
    Box(
        modifier = modifier.background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value.toString(),
            fontSize = 20.sp,
            color = Color.White
        )
    }
}

@Composable
fun NumberColumn(
    range: IntRange,
    current: Int,
    modifier: Modifier = Modifier
) {
    val cellSize = 40.dp
    val mid = (range.last - range.first) / 2f

    val reset = current == range.first
    val offset by animateDpAsState(
        targetValue = cellSize * (mid - current),
        animationSpec = if (reset) {
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        } else {
            tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        }
    )

    Column(
        modifier = modifier
            .offset(y = offset)
            .clip(RoundedCornerShape(percent = 15))
    ) {
        range.forEach { num ->
            Number(num, num == current, Modifier.size(size = cellSize))
        }
    }
}

@Composable
@Preview(name = "Kat4X", showBackground = true)
fun ClockPreview() {
    fun currentTime(): Time { // 1
        val cal = Calendar.getInstance()
        return Time(
            hour = cal.get(Calendar.HOUR_OF_DAY),
            minutes = cal.get(Calendar.MINUTE),
            seconds = cal.get(Calendar.SECOND)
        )
    }

    val time = remember { mutableStateOf(currentTime()) }

    LaunchedEffect(0) {
        while (true) {
            time.value = currentTime()
            delay(1000)
        }
    }

    Clock(time = time.value)

//    CoolComposeClockTheme {
//    }
}

