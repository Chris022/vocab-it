package com.chris022.vocabit.data

import com.chris022.vocabit.data.source.FlashCard
import com.chris022.vocabit.data.source.FlashCardDao
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultFlashCardRepository @Inject constructor(
    private val flashCardSource: FlashCardDao,
) : FlashCardRepository{
    override suspend fun getFlashCard(index: Int): FlashCard? {
        return flashCardSource.findById(index)
    }

    override suspend fun createFlashCard(sideA:String,sideB:String){
        flashCardSource.insert(
            FlashCard(
                sideA = sideA,
                sideB = sideB
            )
        )
    }
}