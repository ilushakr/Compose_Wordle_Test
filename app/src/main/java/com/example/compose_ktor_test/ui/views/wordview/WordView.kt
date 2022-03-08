package com.example.compose_ktor_test.ui.views.wordview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ktor_test.presentation.postsList.WordViewModel

@Composable
fun WordsView(
    modifier: Modifier = Modifier,
    viewModel: WordViewModel = hiltViewModel()
) {

    val state = viewModel.wordState.collectAsState()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(state.value.wordStates.size) {
            CustomizedTextLine(
                wordViewRowState = state.value.wordStates[it]
            )
        }
    }
}


//@Composable
//fun animatedLetter(
//    modifier: Modifier = Modifier,
//    position: Int,
//    letter: String
//): () -> Unit {
//
//    var letterState by remember {
//        mutableStateOf(
//            LetterState(
//                color = Color.Gray,
//                angle = 0f,
//                textColor = Color.White
//            )
//        )
//    }
////    var sizeState by remember {
////        mutableStateOf(40.dp)
////    }
//
//    var state by remember { mutableStateOf(false) }
//
//
//    if (state) {
//        letterState = letterState.copy(
//            angle = 90f
//        )
//        state = false
//    }
////    var colorState by remember {
////        mutableStateOf(Color.Gray)
////    }
//
//    val animateY by animateFloatAsState(
//        targetValue = letterState.angle,
//        animationSpec = tween(durationMillis = 400, delayMillis = position * 200),
//        finishedListener = {
////            letterState = letterState.copy(
////                angle = 90f,
////                color = Color.Red
////            )
//            letterState = letterState.copy(
//                angle = 0f,
//                color = Color.Red
//            )
//        }
//    )
//
//    if(letterState.angle == 90f) {
//        letterState = letterState.copy(
//            angle = -90f
//        )
//    }
//    Box(
//        modifier = modifier.size(40.dp, 60.dp)
//            .graphicsLayer {
//                rotationY = animateY
//            },
//        contentAlignment = Alignment.Center
//    ) {
//        Box(
//            modifier = modifier
//                .fillMaxSize()
//                .background(letterState.color),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = letter,
//                textAlign = TextAlign.Center,
//                color = Color.White,
//                fontSize = 30.sp
//            )
//        }
//    }
//
//    return {
//        state = true
//    }
//
//}
//
//data class LetterState(
//    var color: Color,
//    val angle: Float,
//    val textColor: Color
//)