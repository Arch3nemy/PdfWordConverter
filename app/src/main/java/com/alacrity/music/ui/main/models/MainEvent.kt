package com.alacrity.music.ui.main.models

import com.alacrity.music.BaseEvent
import com.alacrity.music.ui.main.MainViewModel

sealed class MainEvent : BaseEvent {

    object EnterScreen : MainEvent()

    object EnterHomeScreen : MainEvent()

    object ConvertFile : MainEvent()

    object OpenDownloadsFolder: MainEvent()

    data class EnterConvertationScreen(val way: String) : MainEvent()

}

fun MainViewModel.enterScreen() {
    obtainEvent(MainEvent.EnterScreen)
}

fun MainViewModel.enterHomeScreen() {
    obtainEvent(MainEvent.EnterHomeScreen)
}

fun MainViewModel.enterConvertScreen(way: String) {
    obtainEvent(MainEvent.EnterConvertationScreen(way))
}

fun MainViewModel.convertFile() {
    obtainEvent(MainEvent.ConvertFile)
}

fun MainViewModel.openDownloadsFolder() {
    obtainEvent(MainEvent.OpenDownloadsFolder)
}

