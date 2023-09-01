package com.chris022.vocabit.data

import com.chris022.vocabit.data.source.Set
import com.chris022.vocabit.data.source.SetAndFlashCards
import com.chris022.vocabit.data.source.SetDao
import com.chris022.vocabit.sets.SetType
import javax.inject.Inject

class DefaultSetRepository @Inject constructor(
    private val setSource: SetDao,
) : SetRepository {
    override suspend fun getAllWithFlashcards(type: SetType): List<SetAndFlashCards> {
        val out = mutableListOf<SetAndFlashCards>()
        //first add all ReadWrite
        if(type == SetType.Reading || type == SetType.Writing || type == SetType.ReadWrite) out.addAll(setSource.allWithFlashcards(SetType.ReadWrite))
        //than add the Read or Write
        if(type == SetType.Reading) out.addAll(setSource.allWithFlashcards(SetType.Reading))
        if(type == SetType.Writing) out.addAll(setSource.allWithFlashcards(SetType.Writing))
        return out
    }

    override suspend fun getAll(type: SetType): List<Set> {
        val out = mutableListOf<Set>()
        //first add all ReadWrite
        if(type == SetType.Reading || type == SetType.Writing || type == SetType.ReadWrite) out.addAll(setSource.all(SetType.ReadWrite))
        //than add the Read or Write
        if(type == SetType.Reading) out.addAll(setSource.all(SetType.Reading))
        if(type == SetType.Writing) out.addAll(setSource.all(SetType.Writing))
        return out
    }

    override suspend fun countFlashcardsInSet(id: Int): Int {
        return setSource.countFlashcards(id)
    }

    override suspend fun delete(id: Int) {
        setSource.delete(id)
    }

    override suspend fun createSet(name: String, type: SetType): Long {
        return setSource.insert(
            Set(
                name = name,
                type = type
            )
        )
    }
}