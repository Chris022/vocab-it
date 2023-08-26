package com.chris022.vocabit.data.source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FlashCard::class,Set::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao

    abstract fun setDao(): SetDao
}
