package com.alacrity.music.bus

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.last
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainBus @Inject constructor() {

    val dataFlow = MutableStateFlow<MainBusEvent>(MainBusEvent.Idle)

    fun insertValue(value: MainBusEvent) { //TODO
        val curValue = dataFlow.value
        Timber.d("Trying to insert $value, current state $curValue equals? ${value == curValue}")


        if(value.isNotEqual(curValue)) {
            dataFlow.value = value
            Timber.d("Inserted $value")
        }

        /*if (value is MainBusEvent.OnFileUploaded) {
            try {
                if (dataFlow.value !is MainBusEvent.OnFileUploaded) {
                    dataFlow.value = value
                }
            } catch (e: Exception) {
                Timber.d("Exception:: $e")
            }
        } else {
            dataFlow.value = value
        }*/
    }

}

sealed class MainBusEvent {
    object Idle : MainBusEvent()
    object OpenDownloadsFolder : MainBusEvent()

    data class ToastAlert(val text: String): MainBusEvent()
    data class PickUpFile(val ext: String) : MainBusEvent()
    data class OnFileUploaded(val file: Result<File>) : MainBusEvent()

    fun isNotEqual(other: MainBusEvent?): Boolean {
        when(this) {
            is Idle -> if(other !is Idle) return true
            is OpenDownloadsFolder -> if(other !is OpenDownloadsFolder) return true
            is PickUpFile -> if(other !is PickUpFile) return true
            is OnFileUploaded -> if(other !is OnFileUploaded) return true
            is ToastAlert -> if(other !is ToastAlert) return true
        }
        return false
    }

}