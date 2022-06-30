package com.alacrity.music.di

import com.alacrity.music.use_cases.ConvertFileUseCase
import com.alacrity.music.use_cases.ConvertFileUseCaseImpl
import com.alacrity.music.use_cases.LoadFileFromDeviceUseCase
import com.alacrity.music.use_cases.LoadFileFromDeviceUseCaseImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface UseCaseModule {

    @Binds
    @Singleton
    fun bindLoadFileUseCase(impl: LoadFileFromDeviceUseCaseImpl): LoadFileFromDeviceUseCase

    @Binds
    @Singleton
    fun bindConvertFileUseCase(impl: ConvertFileUseCaseImpl): ConvertFileUseCase

}