package com.alacrity.music.use_cases

import com.alacrity.music.Repository
import kotlinx.coroutines.CancellationException
import java.io.File
import javax.inject.Inject

class ConvertFileUseCaseImpl @Inject constructor(
    private val repository: Repository
): ConvertFileUseCase {

    override suspend fun invoke(file: File): Result<File> = try {
        val file1 = repository.convertFile(file)
        Result.success(file1)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Result.failure(e)
    }




}