package com.chris022.vocabit.data

import com.chris022.vocabit.data.source.FlashCard

interface FlashCardRepository {
    suspend fun getFlashCard(index: Int): FlashCard?

    suspend fun createFlashCard(sideA:String, sideB:String): Unit
}