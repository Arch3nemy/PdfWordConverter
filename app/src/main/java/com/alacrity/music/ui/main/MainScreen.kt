package com.alacrity.music.ui.main

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alacrity.music.theme.ColorButtonDefault
import com.alacrity.music.ui.main.models.*
import com.alacrity.music.ui.main.views.*
import com.alacrity.music.util.getScreenSize
import com.alacrity.music.view_states.MainViewState

@Composable
fun MainScreen(
    context: Context,
    viewModel: MainViewModel,
) {

    val state by viewModel.viewState.collectAsState()
    val fileState by viewModel.dataFlow.collectAsState()

    MainWrapper(viewModel = viewModel) {
        when (state) {
            MainViewState.MainScreen -> {
                HomeScreen(onItemClick = { viewModel.enterConvertScreen(it) })
            }

            MainViewState.Loading -> {
                LoadingAnimation(circleColor = ColorButtonDefault)
            }

            is MainViewState.Error -> {
                ErrorScreen(
                    (state as MainViewState.Error).exception,
                    (state as MainViewState.Error).message
                )
            }

            is MainViewState.ConvertationScreen -> {
                val screen = state as MainViewState.ConvertationScreen
                val ext = if(screen.way == "WordToPdf") "docx" else "pdf"
                ConvertationScreen(screen.way,
                    fileName = "${screen.file.name}.${screen.file.extension}$ext",
                    onBackClick = { viewModel.enterHomeScreen() },
                    onConvertClick = { viewModel.convertFile() })
            }

            is MainViewState.ConvertingFileResultScreen -> {
                ConvertingFileResultScreen(
                    fileState = fileState,
                    onBackClick = { viewModel.enterHomeScreen() },
                onOpenDownloadsFolder = { viewModel.openDownloadsFolder() })
            }

            else -> Unit
        }

        LaunchedEffect(key1 = state, block = {
            viewModel.enterScreen()
        })

    }
}

@Composable
fun MainWrapper(viewModel: MainViewModel, content: @Composable () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    with(getScreenSize<Float>()) {
        content()
        /* Scaffold(
             scaffoldState = scaffoldState,
             drawerContent = {
                 *//*AppDrawer(
                    closeDrawer = { coroutineScope.launch { scaffoldState.drawerState.close() } },
                    width = (first / 5 - 20).dp,
                    refresh = { viewModel.obtainEvent(HomeEvent.OnRefreshClicked) }
                )*//*
            },
            drawerShape = customShape(first, second),
        ) {

        }*/
    }
}