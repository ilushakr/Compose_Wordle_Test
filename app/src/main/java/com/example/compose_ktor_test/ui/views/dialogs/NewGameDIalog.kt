package com.example.compose_ktor_test.ui.views.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.chargemap.compose.numberpicker.NumberPicker
import com.example.compose_ktor_test.presentation.postsList.WordViewModel
import com.example.compose_ktor_test.utils.DialogEvent

@Composable
fun NewGameDialog(
    dialogEvent: DialogEvent,
    onDismiss: () -> Unit,
    onPositiveClick: (Int) -> Unit,
    viewModel: WordViewModel = hiltViewModel()
) {

    var state by remember {
        mutableStateOf(viewModel.numberOfRows)
    }

    Dialog(onDismissRequest = onDismiss) {

        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {

            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                DialogEventView(dialogEvent = dialogEvent)

                Text(
                    text = "Выберите количество попыток",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                NumberPicker(
                    value = state,
                    range = 6..12,
                    onValueChange = {
                        state = it
                    }
                )

                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        onPositiveClick(state)
                    }) {
                        Text(text = "Новая игра!")
                    }
                }
            }
        }
    }
}

@Composable
fun DialogEventView(
    dialogEvent: DialogEvent,
    viewModel: WordViewModel = hiltViewModel()
) {
    when (dialogEvent) {
        DialogEvent.EndGameInterrupted -> Unit
        is DialogEvent.EndGameLose -> {
            Column(
                Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Вы проиграли("
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )
                Text(
                    text = "Слово было: ${viewModel.currentWord}"
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )
            }
        }
        DialogEvent.EndGameWin -> {
            Column(
                Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Вы выиграли) Поздравляю"
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                )
            }
        }
    }
}
