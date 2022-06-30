package com.alacrity.music.ui.main.models

import com.alacrity.music.exceptions.BaseException
import java.io.File

sealed class MainDataState {

    object EmptyData: MainDataState()

    data class FileLoaded(val file: File, val convertedFile: File? = null): MainDataState()

    data class ErrorLoadingFile(val error: Throwable, val message: String? = null): MainDataState()

}

fun MainDataState.getFile(): File? = when(this) {
    is MainDataState.EmptyData -> null
    is MainDataState.ErrorLoadingFile -> null
    is MainDataState.FileLoaded -> file
}

fun MainDataState.getErrorAndMessage(): Pair<Throwable, String?>? = when(this) {
    is MainDataState.EmptyData -> Pair(BaseException("This can't really happen"), null)
    is MainDataState.ErrorLoadingFile -> Pair(error, message)
    is MainDataState.FileLoaded -> null
}