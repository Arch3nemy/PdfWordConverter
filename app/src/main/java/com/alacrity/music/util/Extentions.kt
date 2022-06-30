package com.alacrity.music.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.io.File

//Returns pair of screen width and height
@Composable
inline fun <reified T> getScreenSize(): Pair<T, T> {
    val configuration = LocalConfiguration.current
    with(configuration) {
        return when (T::class) {
            Int::class -> Pair(screenWidthDp as T, screenHeightDp as T)
            Dp::class -> Pair(screenWidthDp.dp as T, screenHeightDp.dp as T)
            else -> Pair(
                with(LocalDensity.current) { screenWidthDp.dp.toPx() } as T,
                with(LocalDensity.current) { screenHeightDp.dp.toPx() } as T)
        }
    }
}

fun File.isValidMsWordFile(): Boolean {
    if (extension.contains("doc") || extension.contains("docx")) return true
    return false
}