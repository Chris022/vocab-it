package com.chris022.vocabit.data.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "flashCard",
    foreignKeys = [ForeignKey(
        entity = Set::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("set_id"),
        onUpdate = ForeignKey.CASCADE,
        onDelete = ForeignKey.CASCADE
    )]
)
data class FlashCard (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "side_a") val sideA: String,
    @ColumnInfo(name = "side_b") val sideB: String,
    @ColumnInfo(name = "set_id") val setId: Int,
    @ColumnInfo(name = "enabled") var enabled: Boolean
)