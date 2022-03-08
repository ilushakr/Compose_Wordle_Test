package com.example.compose_ktor_test.ui.views.wordview

import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

data class WordViewLetterState(
    val backgroundColor: Color,
    val border: BorderStroke?,
    val textColor: Color,
)
