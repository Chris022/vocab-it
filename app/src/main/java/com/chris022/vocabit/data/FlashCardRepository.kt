package com.chris022.vocabit.data

import com.chris022.vocabit.data.source.FlashCard

interface FlashCardRepository {
    suspend fun getFlashcard(index: Int): FlashCard?

    suspend fun countFlashcards(): Int

    suspend fun createFlashCard(setId:Int, sideA:String, sideB:String): Unit


}