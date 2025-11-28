package com.example.examplemvvm.ui.screens.historial

enum class UiMessageType { INFO, ERROR }

data class UiMessage(
    val text: String,
    val type: UiMessageType
)

