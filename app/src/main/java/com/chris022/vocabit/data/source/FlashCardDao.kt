package com.chris022.vocabit.data.source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashCard WHERE id = :id")
    suspend fun findById(id: Int): FlashCard?

    @Query("SELECT * FROM flashCard WHERE set_id=:setId ORDER BY id LIMIT 1 OFFSET :n")
    suspend fun findNth(n: Int, setId: Int): FlashCard?

    @Query("SELECT Count(id) FROM flashCard")
    suspend fun count(): Int

    @Insert
    suspend fun insert(flashCard: FlashCard): Long
}