package com.alacrity.music.use_cases

import java.io.File
import javax.inject.Singleton

@Singleton
interface LoadFileFromDeviceUseCase {

    suspend operator fun invoke(): Result<File>

}