package com.chris022.vocabit.data.source

import androidx.room.TypeConverter
import com.chris022.vocabit.features.sets.SetType

class Converters {

    @TypeConverter
    fun toSetType(value: String) = enumValueOf<SetType>(value)

    @TypeConverter
    fun fromSetType(value: SetType) = value.name
}