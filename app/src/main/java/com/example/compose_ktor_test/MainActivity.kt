package com.example.compose_ktor_test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose_ktor_test.presentation.postsList.WordViewModel
import com.example.compose_ktor_test.ui.theme.Compose_KTor_TestTheme
import com.example.compose_ktor_test.ui.views.KeyboardView
import com.example.compose_ktor_test.ui.views.dialogs.NewGameDialog
import com.example.compose_ktor_test.ui.views.wordview.WordsView
import com.example.compose_ktor_test.utils.DialogEvent
import com.example.compose_ktor_test.utils.UiEvent
import kotlinx.coroutines.flow.collect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Compose_KTor_TestTheme {
                Main()
            }
        }
    }
}


@Composable
fun Main(viewModel: WordViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val dialogEvent = viewModel.dialogEvent.collectAsState()
    val scaffoldState = rememberScaffoldState()

    dialogEvent.value?.let { event ->
        NewGameDialog(
            dialogEvent = event,
            onDismiss = {
                viewModel.changeDialogEventState(null)
            },
            onPositiveClick = { numberOfRows ->
                viewModel.newGame(numberOfRows)
            }
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { snackbarHostState: SnackbarHostState ->
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData: SnackbarData ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(2.dp, Color.White),
                        modifier = Modifier
                            .padding(16.dp)
                            .wrapContentSize()
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(imageVector = Icons.Default.Notifications, contentDescription = "")
                            Text(text = snackbarData.message)
                        }
                    }
                }
            )
        },
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Wordle")
                },

                actions = {
                    if(BuildConfig.DEBUG){
                        IconButton(onClick = {
                            Toast.makeText(context, viewModel.currentWord, Toast.LENGTH_SHORT)
                                .show()
                        }) {
                            Icon(
                                Icons.Outlined.Info,
                                contentDescription = "Show current word"
                            )
                        }
                    }

                    IconButton(onClick = {
                        viewModel.changeDialogEventState(DialogEvent.EndGameInterrupted)
                    }) {
                        Icon(
                            Icons.Filled.MoreVert,
                            contentDescription = "New game"
                        )
                    }
                },
                elevation = AppBarDefaults.TopAppBarElevation
            )
        }
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (keyboard, columnView) = createRefs()

            WordsView(
                modifier = Modifier
                    .constrainAs(columnView) {
                        top.linkTo(parent.top)
                        bottom.linkTo(keyboard.top)
                        height = Dimension.fillToConstraints
                        width = Dimension.matchParent
                    }
                    .padding(8.dp)
            )

            KeyboardView(
                modifier = Modifier
                    .constrainAs(keyboard) { bottom.linkTo(parent.bottom, margin = 8.dp) }
                    .padding(horizontal = 8.dp),
                onClick = { letter ->
                    viewModel.addLetter(letter)
                }
            )

        }
    }

}