package com.chris022.vocabit

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class ApplicationModule {
    @Binds
    abstract fun logger(logger: ConsoleLogger):ILogger
}