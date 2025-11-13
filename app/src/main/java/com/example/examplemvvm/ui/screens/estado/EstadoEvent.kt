package com.example.examplemvvm.ui.screens.estado

sealed class EstadoEvent {

    object btnBackClicked: EstadoEvent()
    object btnMuyBienClicked : EstadoEvent()
    object btnBienClicked : EstadoEvent()
    object btnNeutroClicked : EstadoEvent()
    object btnMalClicked: EstadoEvent()
    object btnMuyMalClicked: EstadoEvent()
}