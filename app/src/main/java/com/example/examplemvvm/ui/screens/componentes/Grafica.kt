package com.example.examplemvvm.ui.screens.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import kotlin.math.roundToInt

@Composable
fun Grafica(
    moodsByDay: List<Int>, // 7 valores (L..D), rango esperado 1..5
    modifier: Modifier = Modifier
) {
    val entries = remember(moodsByDay) {
        val base = moodsByDay.mapIndexed { i, v -> FloatEntry(i.toFloat(), v.toFloat()) }
        base + listOf(FloatEntry(0f, 1f), FloatEntry(0f, 5f)) // fuerza rango Y
    }
    val model = remember(entries) { entryModelOf(entries) }

    val days = listOf("L", "M", "M", "J", "V", "S", "D")
    val yEmoji = mapOf(1 to "😭", 2 to "🥲", 3 to "🙂", 4 to "😊", 5 to "😄")

    val bottomAxis = rememberBottomAxis(
        valueFormatter = { value, _ ->
            val idx = value.roundToInt()
            days.getOrNull(idx) ?: ""
        },
        guideline = null
    )

    val startAxis = rememberStartAxis(
        valueFormatter = { value, _ ->
            val intVal = value.roundToInt()
            yEmoji[intVal] ?: ""
        },
        guideline = null
    )

    Chart(
        chart = columnChart(spacing = 10.dp),
        model = model,
        startAxis = startAxis,
        bottomAxis = bottomAxis,
        horizontalLayout = HorizontalLayout.FullWidth(),
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp)
    )
}