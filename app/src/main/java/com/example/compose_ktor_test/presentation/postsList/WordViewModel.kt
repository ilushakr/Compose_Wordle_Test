package com.example.compose_ktor_test.presentation.postsList

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ktor_test.data.WordRepository
import com.example.compose_ktor_test.ui.views.wordview.WordViewLetterState
import com.example.compose_ktor_test.ui.views.wordview.WordViewRowState
import com.example.compose_ktor_test.utils.DialogEvent
import com.example.compose_ktor_test.utils.MainState
import com.example.compose_ktor_test.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordRepository: WordRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var currentWord: String = ""
        private set

    var numberOfRows: Int = savedStateHandle["number_of_rows"] ?: 6
        private set

    private var stateCounter: Int? = savedStateHandle["state_counter"] ?: 0

    private var _wordState: MutableStateFlow<MainState> = savedStateHandle["word_state"] ?: MutableStateFlow(
        MainState(
            List(numberOfRows) {
                WordViewRowState(
                    letters = "",
                    lettersStates = MutableList(5) {
                        WordViewLetterState(
                            textColor = Color.Black,
                            border = BorderStroke(2.dp, Color.Black),
                            backgroundColor = Color.White
                        )
                    }
                )
            }
        )
    )

    val wordState: StateFlow<MainState> = _wordState.asStateFlow()

    private val currentWordState: WordViewRowState
        get() = _wordState.value.wordStates[stateCounter!!]

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _dialogEvent = MutableStateFlow<DialogEvent?>(null)
    val dialogEvent: StateFlow<DialogEvent?> = _dialogEvent.asStateFlow()

    init {
        viewModelScope.launch {
            val t: String? = savedStateHandle["current_word"]
            Timber.d(t.toString())
            currentWord = savedStateHandle["current_word"] ?: wordRepository.getRandomWord().word
            savedStateHandle["current_word"] = currentWord
        }
    }


    private fun checkWord(word: String) {
        if(stateCounter == null) return
        if (word == currentWord) {
            _wordState.value = _wordState.value.updateStates(stateCounter!!, MutableList(5) {
                WordViewLetterState(
                    textColor = Color.White,
                    backgroundColor = Color.Green,
                    border = null
                )
            })
            savedStateHandle["word_state"] = _wordState.value
            _dialogEvent.value = DialogEvent.EndGameWin
            stateCounter = null
            savedStateHandle["state_counter"] = null
            return
        }
        viewModelScope.launch {
            val result = wordRepository.getWord(word)
            if (result == null) {
                sendUiEvent(UiEvent.ShowSnackbar("Такого слова нет("))
            } else {
                _wordState.value = _wordState.value.updateStates(stateCounter!!, MutableList(5) {
                    WordViewLetterState(
                        textColor = Color.White,
                        backgroundColor = getColor(it),
                        border = null
                    )
                })
                savedStateHandle["word_state"] = _wordState.value
                stateCounter = stateCounter!! + 1
                savedStateHandle["state_counter"] = stateCounter
                if(stateCounter == numberOfRows){
                    _dialogEvent.value = DialogEvent.EndGameLose(word = currentWord)
                    stateCounter = null
                    savedStateHandle["state_counter"] = null
                }
            }
        }
    }

    fun addLetter(letter: String) {
        if(stateCounter == null) return
        when (letter) {
            "enter" -> {
                if (currentWordState.letters.length != 5) return
                if(stateCounter == numberOfRows) _dialogEvent.value = DialogEvent.EndGameLose(word = currentWord)
                checkWord(currentWordState.letters)
            }
            "delete" -> {
                if (currentWordState.letters == "") return
                _wordState.value = _wordState.value.updateLetters(
                    stateCounter!!,
                    currentWordState.letters.dropLast(1)
                )
                savedStateHandle["word_state"] = _wordState.value
            }
            else -> {
                if (currentWordState.letters.length == 5) return
                _wordState.value = _wordState.value.updateLetters(
                    stateCounter!!,
                    currentWordState.letters + letter
                )
                savedStateHandle["word_state"] = _wordState.value
            }
        }
    }

    fun newGame(numberOfRows: Int) {
        this.numberOfRows = numberOfRows
        savedStateHandle["number_of_rows"] = numberOfRows
        viewModelScope.launch {
            currentWord = wordRepository.getRandomWord().word
            savedStateHandle["current_word"] = currentWord
            stateCounter = 0
            savedStateHandle["state_counter"] = stateCounter
            _wordState.value = MainState(
                List(numberOfRows) {
                    WordViewRowState(
                        letters = "",
                        lettersStates = MutableList(5) {
                            WordViewLetterState(
                                textColor = Color.Black,
                                border = BorderStroke(2.dp, Color.Black),
                                backgroundColor = Color.White
                            )
                        }
                    )
                }
            )
            savedStateHandle["word_state"] = _wordState.value
            _dialogEvent.value = null
        }
    }

    private fun getColor(index: Int): Color {
        val currentLetter = currentWordState.letters[index].toString()
        val indices = Regex(currentLetter).findAll(currentWord).map { it.range.first }.toList()
        if (indices.isEmpty()) return Color.Gray
        if (index in indices) return Color.Green
        return Color.Yellow

    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    fun changeDialogEventState(dialogEvent: DialogEvent?) {
        _dialogEvent.value = dialogEvent
    }
}