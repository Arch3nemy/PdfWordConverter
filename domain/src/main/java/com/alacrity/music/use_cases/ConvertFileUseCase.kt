package com.alacrity.music.use_cases

import java.io.File
import javax.inject.Singleton

@Singleton
interface ConvertFileUseCase {

    suspend operator fun invoke(file: File): Result<File>

}