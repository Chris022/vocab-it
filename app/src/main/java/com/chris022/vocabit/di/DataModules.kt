package com.chris022.vocabit.di

import android.content.Context
import androidx.room.Room
import com.chris022.vocabit.data.DefaultFlashCardRepository
import com.chris022.vocabit.data.FlashCardRepository
import com.chris022.vocabit.data.source.AppDatabase
import com.chris022.vocabit.data.source.FlashCardDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindFlashCardRepository(repository: DefaultFlashCardRepository): FlashCardRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    @Provides
    fun provideFlashCardDao(database: AppDatabase): FlashCardDao = database.flashCardDao()
}