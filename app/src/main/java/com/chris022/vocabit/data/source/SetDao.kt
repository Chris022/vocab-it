package com.chris022.vocabit.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import com.chris022.vocabit.sets.SetType

@TypeConverters(Converters::class)
@Dao
interface SetDao {

    @Query("SELECT * FROM `set` WHERE type=:type")
    suspend fun all(type: SetType): List<Set>

    @Transaction
    @Query("SELECT * FROM `set` WHERE type=:type")
    suspend fun allWithFlashcards(type: SetType): List<SetAndFlashCards>

    @Insert
    suspend fun insert(set: Set): Long
}