package com.example.examplemvvm.ui.screens.rutinas
sealed class RutinaEvent{
    object btnRespiracionProfundaClicked: RutinaEvent()
    object btnREspiracionCompletaClicked: RutinaEvent()
    object btnRespiracionDormirMejorClicked: RutinaEvent()
    object btnRespiracionAbejaClicked: RutinaEvent()
    object btnBackDasboardClicked : RutinaEvent()
}

