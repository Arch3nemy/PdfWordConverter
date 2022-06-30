package com.alacrity.music.ui.main

import androidx.lifecycle.viewModelScope
import com.alacrity.music.bus.MainBus
import com.alacrity.music.bus.MainBusEvent
import com.alacrity.music.ui.main.models.MainDataState
import com.alacrity.music.ui.main.models.MainEvent
import com.alacrity.music.ui.main.models.getFile
import com.alacrity.music.use_cases.ConvertFileUseCase
import com.alacrity.music.util.BaseViewModel
import com.alacrity.music.util.isDocument
import com.alacrity.music.view_states.MainViewState
import com.alacrity.music.view_states.MainViewState.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val convertFileUseCase: ConvertFileUseCase,
    private val bus: MainBus
) : BaseViewModel<MainEvent, MainViewState>(MainScreen) {

    val viewState: StateFlow<MainViewState> = _viewState

    private val _dataFlow: MutableStateFlow<MainDataState> =
        MutableStateFlow(MainDataState.EmptyData)
    val dataFlow = _dataFlow as StateFlow<MainDataState>

    override fun obtainEvent(event: MainEvent) {
        when (val currentState = _viewState.value) {
            is Loading -> currentState.reduce(event)
            is Error -> currentState.reduce(event)
            is MainScreen -> currentState.reduce(event)
            is ConvertationScreen -> currentState.reduce(event)
            is ConvertingFileResultScreen -> currentState.reduce(event)
        }
    }

    init {
        subscribeToBus()
    }

    private fun Loading.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            is MainEvent.EnterScreen -> {
            }
            else -> Unit
        }
    }

    private fun Error.reduce(event: MainEvent) {
        logReduce(event)

    }

    private fun MainScreen.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            is MainEvent.EnterConvertationScreen -> {
                val fileExt = if(event.way == "PdfToWord") "pdf" else "doc"
                _viewState.value = Loading
                bus.insertValue(MainBusEvent.PickUpFile(fileExt))
            }
            else -> Unit
        }
    }

    private fun ConvertationScreen.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            is MainEvent.EnterHomeScreen -> {
                _viewState.value = MainScreen
            }
            is MainEvent.ConvertFile -> {
                _viewState.value = Loading
                convertFile(_dataFlow.value.getFile()!!)
            }
            else -> Unit
        }
    }

    private fun ConvertingFileResultScreen.reduce(event: MainEvent) {
        logReduce(event)
        when (event) {
            is MainEvent.EnterHomeScreen -> {
                _viewState.value = MainScreen
            }
            is MainEvent.OpenDownloadsFolder -> {
                bus.insertValue(MainBusEvent.OpenDownloadsFolder)
            }
            else -> Unit
        }
    }

   /* private fun loadData(way: String) {
        viewModelScope.launch {
            loadFileFromDeviceUseCase().fold(
                onSuccess = {
                    _dataFlow.value = MainDataState.FileLoaded(it)
                    _viewState.value = ConvertationScreen(
                        way = way,
                        file = it
                    )
                },
                onFailure = {
                    _dataFlow.value = MainDataState.ErrorLoadingFile(
                        error = it,
                        message = "Error retrieving file"
                    )
                    _viewState.value = Error(it, "Error retrieving file")
                })
        }
    }*/

    private fun convertFile(file: File) {
        viewModelScope.launch {
            convertFileUseCase(file).fold(
                onSuccess = {
                    _viewState.value = ConvertingFileResultScreen(Result.success(it))
                    val oldFile = _dataFlow.value.getFile()
                    _dataFlow.value = MainDataState.FileLoaded(oldFile!!, it)
                },
                onFailure = {
                    _viewState.value = ConvertingFileResultScreen(Result.failure(it))
                    _dataFlow.value = MainDataState.ErrorLoadingFile(it, "Error converting file")
                }
            )
        }
    }

    var job: Job? = null

    private fun subscribeToBus() {
        if(job?.isActive == true) {
            job?.cancel()
        }
         job = viewModelScope.launch {
            bus.dataFlow.collect {
                Timber.d("OnCollectDataFlow:: $it")
                when (it) {
                    is MainBusEvent.OnFileUploaded -> {
                        it.file.fold(
                                onSuccess = { file ->
                                    _dataFlow.value = MainDataState.FileLoaded(file)
                                    if(!file.isDocument()) {
                                        bus.insertValue(MainBusEvent.ToastAlert("Invalid file"))
                                        _viewState.value = MainScreen
                                        return@fold
                                    }
                                    _viewState.value = ConvertationScreen(
                                        way = if(it.way == "pdf") "PdfToWord" else "WordToPdf",
                                        file = file
                                    )
                                },
                                onFailure = { error ->
                                    _dataFlow.value = MainDataState.ErrorLoadingFile(
                                        error = error,
                                        message = "Error retrieving file"
                                    )
                                    _viewState.value = MainScreen
                                }
                            )


                    }
                    else -> Unit
                }
            }
        }
    }

}

