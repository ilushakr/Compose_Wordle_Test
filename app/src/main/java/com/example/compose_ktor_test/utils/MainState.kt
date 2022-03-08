package com.example.compose_ktor_test.utils

import com.example.compose_ktor_test.ui.views.wordview.WordViewLetterState
import com.example.compose_ktor_test.ui.views.wordview.WordViewRowState
import java.io.Serializable

data class MainState(
    val wordStates: List<WordViewRowState>
) : Serializable{
    fun updateLetters(index: Int, letters: String) = MainState(
        wordStates = wordStates.mapIndexed { i, wordViewRowState ->
            if (index == i) {
                WordViewRowState(
                    letters = letters,
                    lettersStates = wordViewRowState.lettersStates
                )
            } else {
                wordViewRowState
            }
        }
    )

    fun updateStates(index: Int, states: List<WordViewLetterState>) = MainState(
        wordStates = wordStates.mapIndexed { i, wordViewRowState ->
            if (index == i) {
                WordViewRowState(
                    letters = wordViewRowState.letters,
                    lettersStates = states
                )
            } else {
                wordViewRowState
            }
        }
    )
}
