package com.chris022.vocabit.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface SetDao {

    @Query("SELECT * FROM `set`")
    suspend fun all(): List<Set>

    @Transaction
    @Query("SELECT * FROM `set`")
    suspend fun allWithFlashcards(): List<SetAndFlashCards>

    @Insert
    suspend fun insert(set: Set): Long
}