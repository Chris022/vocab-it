package com.chris022.vocabit.data

import com.chris022.vocabit.data.source.Set
import com.chris022.vocabit.data.source.SetAndFlashCards
import com.chris022.vocabit.sets.SetType

interface SetRepository {
    suspend fun getAllWithFlashcards(type: SetType) : List<SetAndFlashCards>

    suspend fun getAll(type: SetType): List<Set>

    suspend fun createSet(name: String, type: SetType): Long
}