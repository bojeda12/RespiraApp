package com.example.examplemvvm.ui.screens.dashboard

import androidx.lifecycle.ViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
//import javax.inject.Inject

//@HiltViewModel
class DashboardViewModel(
//@Inject constructor(private val dao: MoodDao
): ViewModel() {

    // MOCK para la etapa de pantallas/navegación:
    private val _weekMoods = MutableStateFlow(listOf(1,2,2,3,3,3,5))
    val weekMoods: StateFlow<List<Int>> = _weekMoods

    // CUANDO CONECTES ROOM (ejemplo):
    // val weekMoods: StateFlow<List<Int>> =
    //     dao.getWeekMoodAverages(System.currentTimeMillis() - 6*24*60*60*1000)
    //        .map { list ->                           // mapear 0..6 → L..D
    //            val byDow = list.associate { it.dow.toInt() to it.avgMood }
    //            val order = listOf(1,2,3,4,5,6,0)    // L..D
    //            order.map { dow -> (byDow[dow] ?: 0f).coerceIn(1f,5f).roundToInt() }
    //        }
    //        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}