package com.example.examplemvvm.ui.screens.historial

sealed class RecomendacionEvent {
    object btnBackClicked : RecomendacionEvent()
    object CargarHorarios : RecomendacionEvent()
}

