package com.example.examplemvvm.ui.screens.estado

sealed class EstadoEvent {

    object btnBackClicked: EstadoEvent()
    data class EstadoSeleccionado(val estado: String) : EstadoEvent()

}