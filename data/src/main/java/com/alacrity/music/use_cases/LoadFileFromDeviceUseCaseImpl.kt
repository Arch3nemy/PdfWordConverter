package com.alacrity.music.use_cases

import com.alacrity.music.Repository
import com.alacrity.music.utils.safeCall
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class LoadFileFromDeviceUseCaseImpl @Inject constructor(
    private val repository: Repository
): LoadFileFromDeviceUseCase {

    override suspend fun invoke(): Result<File> = try {
        val file = repository.loadFileFromDevice()
        Result.success(file)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Result.failure(e)
    }

}