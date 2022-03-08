package com.example.compose_ktor_test.ui.views.wordview

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_ktor_test.utils.Utils.getOrEmpty

@Composable
fun CustomizedTextLine(
    modifier: Modifier = Modifier,
    wordViewRowState: WordViewRowState
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (0 until 5).forEach {
            val letterState = wordViewRowState.lettersStates[it]
            Card(
                modifier = Modifier.size(60.dp),
                border = letterState.border,
                backgroundColor = letterState.backgroundColor
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = wordViewRowState.letters.getOrEmpty(it),
                        textAlign = TextAlign.Center,
                        color = letterState.textColor,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

}