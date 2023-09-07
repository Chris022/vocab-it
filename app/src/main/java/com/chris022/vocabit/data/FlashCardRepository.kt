package com.chris022.vocabit.data

import com.chris022.vocabit.data.source.FlashCard

interface FlashCardRepository {
    suspend fun getFlashcard(n: Int, setId: Int): FlashCard?

    suspend fun getEnabledFlashcardsForSet(setId: Int): List<FlashCard>

    suspend fun toggleFlashcardEnabled(id: Int): FlashCard?

    suspend fun getAllFlashcards(setId: Int): List<FlashCard>

    suspend fun countFlashcards(): Int

    suspend fun createFlashCard(setId:Int, sideA:String, sideB:String): Unit


}