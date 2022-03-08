package com.example.compose_ktor_test.ui.views.wordview


data class WordViewRowState(
    var letters: String,
    var lettersStates: List<WordViewLetterState>
)
