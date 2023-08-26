package com.chris022.vocabit.data

import com.chris022.vocabit.data.source.Set
import com.chris022.vocabit.data.source.SetAndFlashCards
import com.chris022.vocabit.data.source.SetDao
import javax.inject.Inject

class DefaultSetRepository @Inject constructor(
    private val setSource: SetDao,
) : SetRepository {
    override suspend fun getAllWithFlashcards(): List<SetAndFlashCards> {
        return setSource.allWithFlashcards()
    }

    override suspend fun getAll(): List<Set> {
        return setSource.all()
    }

    override suspend fun createSet(name: String): Long {
        return setSource.insert(
            Set(
                name = name
            )
        )
    }
}