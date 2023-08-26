package com.chris022.vocabit.data.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "flashCard"
)
data class FlashCard (
    @ColumnInfo(name = "side_a") val sideA: String,
    @ColumnInfo(name = "side_b") val sideB: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}