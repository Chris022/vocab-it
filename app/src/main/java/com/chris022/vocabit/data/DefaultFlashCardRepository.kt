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
    override suspend fun getFlashcard(n: Int, setId: Int): FlashCard? {
        return flashCardSource.findNthEnabled(n,setId)
    }

    override suspend fun toggleFlashcardEnabled(id: Int): FlashCard? {
        val flashcard = flashCardSource.findById(id) ?: return null

        flashcard.enabled = !(flashcard.enabled);

        flashCardSource.updateFlashcard(flashcard)

        return flashcard
    }

    override suspend fun getAllFlashcards(setId: Int): List<FlashCard>{
        return flashCardSource.findAll(setId);
    }

    override suspend fun countFlashcards(): Int {
        return flashCardSource.count()
    }

    override suspend fun createFlashCard(setId: Int, sideA:String,sideB:String){
        flashCardSource.insert(
            FlashCard(
                sideA = sideA,
                sideB = sideB,
                setId = setId,
                enabled = true
            )
        )
    }
}