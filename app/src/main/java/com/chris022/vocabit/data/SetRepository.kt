package com.chris022.vocabit.data

import com.chris022.vocabit.data.source.Set
import com.chris022.vocabit.data.source.SetAndFlashCards
import com.chris022.vocabit.features.sets.SetType

interface SetRepository {
    suspend fun getAllWithFlashcards(type: SetType) : List<SetAndFlashCards>

    suspend fun countFlashcardsInSet(id: Int): Int

    suspend fun delete(id: Int)

    suspend fun getAll(type: SetType): List<Set>

    suspend fun createSet(name: String, type: SetType): Long
}