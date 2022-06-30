package com.alacrity.music.di

import com.alacrity.music.App
import com.alacrity.music.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, CoroutineModule::class, NetworkModule::class, UseCaseModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(app: App)

}