package com.example.compose_ktor_test.ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_ktor_test.utils.Utils.orderedCyrillicCharacters

@Composable
fun KeyboardView(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val firstString =
                orderedCyrillicCharacters.substring(0, orderedCyrillicCharacters.indexOf("Ъ") + 1)
            firstString.forEach {
                KeyboardLetter(letter = it.toString(), onClick = onClick)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val secondString =
                orderedCyrillicCharacters.substring(
                    orderedCyrillicCharacters.indexOf("Ъ") + 1,
                    orderedCyrillicCharacters.indexOf("Э") + 1
                )
            secondString.forEach {
                KeyboardLetter(letter = it.toString(), onClick = onClick)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            KeyboardLetter(
                description = "enter",
                icon = Icons.Default.Done,
                onClick = onClick
            )

            val thirdString =
                orderedCyrillicCharacters.substring(
                    orderedCyrillicCharacters.indexOf("Э") + 1,
                    orderedCyrillicCharacters.length
                )

            thirdString.forEach {
                KeyboardLetter(letter = it.toString(), onClick = onClick)
            }

            KeyboardLetter(
                description = "delete",
                icon = Icons.Default.ArrowBack,
                onClick = onClick
            )
        }
    }
}

@Composable
fun KeyboardLetter(
    letter: String,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .width(LocalConfiguration.current.screenWidthDp.dp / 14)
            .background(Color.LightGray)
            .clickable {
                onClick.invoke(letter)
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = letter,
            fontSize = 16.sp
        )
    }
}

@Composable
fun KeyboardLetter(
    description: String,
    icon: ImageVector,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .height(60.dp)
            .width(LocalConfiguration.current.screenWidthDp.dp / 14)
            .background(Color.LightGray)
            .clickable {
                onClick.invoke(description)
            },
        contentAlignment = Alignment.Center,
    ) {
        Icon(icon, contentDescription = description)
    }
}