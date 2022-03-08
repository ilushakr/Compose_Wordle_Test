package com.example.compose_ktor_test.utils

sealed class UiEvent {
    data class ShowSnackbar(val message: String): UiEvent()
}