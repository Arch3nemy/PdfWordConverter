package com.alacrity.music.view_states

import java.io.File


sealed class MainViewState: BaseViewState {
    object Loading : MainViewState()
    object MainScreen: MainViewState()

    data class Error(val exception: Throwable? = null, val message: String? = null) : MainViewState()
    data class ConvertationScreen(val way: String, val file: File): MainViewState()
    data class ConvertingFileResultScreen(val result: Result<File>): MainViewState()
}