package com.example.examplemvvm.ui.screens.componentes
// 📦 Jetpack Compose
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// 📊 Vico (compose + core)
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart

import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf

// 🧮 Kotlin stdlib
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt





// Compose
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.runtime.remember

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

// Vico (v1.14)
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart

import com.patrykandpatrick.vico.core.entry.entryModelOf

/**
 * Gráfica de estado de ánimo (L..D) con eje Y en emojis (1..5).
 * - Ahora pinta con la lista que reciba.
 * - Después, sólo conecta weekMoods (Flow de Room) desde tu ViewModel.
 */
@Composable
fun Grafica(
    moodsByDay: List<Int>,               // 7 valores (L..D), rango esperado 1..5
    modifier: Modifier = Modifier
) {
    // Puntos X=0..6, Y=1..5
    val entries = remember(moodsByDay) {
        moodsByDay.mapIndexed { i, v -> FloatEntry(i.toFloat(), v.toFloat()) }
    }
    val model = remember(entries) { entryModelOf(entries) }

    // Etiquetas de ejes
    val days = listOf("L","M","M","J","V","S","D")
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
            yEmoji[value.roundToInt()] ?: ""
        },
        guideline = null
    )

    // Estilo de la línea
    val lineSpec = LineChart.LineSpec(lineThicknessDp = 4f)

    Chart(
        chart = lineChart(lines = listOf(lineSpec), spacing = 16.dp),
        model = model,
        startAxis = startAxis,
        bottomAxis = bottomAxis,
        horizontalLayout = HorizontalLayout.FullWidth(),
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 8.dp)
    )
}

/*@Preview(showBackground = true)
@Composable
private fun MoodLineChartPreview() {
    // Mock (L..D)
    val week = listOf(1, 2, 2, 3, 3, 3, 5)
    Grafica(moodsByDay = week)
}*/
