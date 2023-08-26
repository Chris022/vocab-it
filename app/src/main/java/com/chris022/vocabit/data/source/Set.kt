package com.chris022.vocabit.data.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "set"
)
data class Set(
    @ColumnInfo(name = "name") val name: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}