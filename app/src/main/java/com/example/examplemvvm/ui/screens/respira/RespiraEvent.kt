package com.example.examplemvvm.ui.screens.respira

sealed class RespiraEvent(){
    object btnDashboardClicked: RespiraEvent()
    object BtnStartClicked : RespiraEvent()
    object BtnStopClicked : RespiraEvent()
}

