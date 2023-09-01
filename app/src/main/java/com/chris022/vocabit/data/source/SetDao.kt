package com.chris022.vocabit.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.TypeConverters
import com.chris022.vocabit.features.sets.SetType

@TypeConverters(Converters::class)
@Dao
interface SetDao {

    @Query("SELECT * FROM `set` WHERE type=:type")
    suspend fun all(type: SetType): List<Set>

    @Query("SELECT COUNT(id) FROM `flashcard` WHERE set_id=:id")
    suspend fun countFlashcards(id: Int): Int

    @Query("DELETE FROM `set` WHERE id=:id")
    suspend fun delete(id: Int)

    @Transaction
    @Query("SELECT * FROM `set` WHERE type=:type")
    suspend fun allWithFlashcards(type: SetType): List<SetAndFlashCards>

    @Insert
    suspend fun insert(set: Set): Long
}