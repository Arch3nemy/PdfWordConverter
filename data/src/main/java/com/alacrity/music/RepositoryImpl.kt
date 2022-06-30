package com.alacrity.music

import kotlinx.coroutines.delay
import java.io.File
import javax.inject.Inject

class RepositoryImpl @Inject constructor() : Repository {

    override suspend fun loadFileFromDevice(): File {
        delay(2000)
        return File("null")
    }

    override suspend fun convertFile(file: File): File {
        delay(2000)
        return  file
    }


}