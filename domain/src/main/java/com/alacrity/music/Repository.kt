package com.alacrity.music

import java.io.File

interface Repository {

    suspend fun loadFileFromDevice(): File

    suspend fun convertFile(file: File): File

}