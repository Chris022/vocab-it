package com.chris022.vocabit.data

import com.chris022.vocabit.data.source.Set
import com.chris022.vocabit.data.source.SetAndFlashCards

interface SetRepository {
    suspend fun getAllWithFlashcards() : List<SetAndFlashCards>

    suspend fun getAll(): List<Set>

    suspend fun createSet(name: String): Long
}