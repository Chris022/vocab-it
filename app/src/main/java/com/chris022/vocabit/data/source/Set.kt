package com.chris022.vocabit.data.source

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

import com.chris022.vocabit.features.sets.SetType

@TypeConverters(Converters::class)
@Entity(
    tableName = "set"
)
data class Set(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: SetType,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}