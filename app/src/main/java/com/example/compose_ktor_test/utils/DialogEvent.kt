package com.example.compose_ktor_test.utils

sealed class DialogEvent{
    object EndGameInterrupted: DialogEvent()
    data class EndGameLose(val word: String): DialogEvent()
    object EndGameWin: DialogEvent()
}
