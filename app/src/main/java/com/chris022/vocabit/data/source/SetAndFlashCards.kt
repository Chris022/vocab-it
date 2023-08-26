package com.chris022.vocabit.data.source

import androidx.room.Embedded
import androidx.room.Relation

data class SetAndFlashCards (
    @Embedded val set: Set,
    @Relation(
        parentColumn = "id",
        entityColumn = "set_id"
    )
    val flashcards: List<FlashCard>
)