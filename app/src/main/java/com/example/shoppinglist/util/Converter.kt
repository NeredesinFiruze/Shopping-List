package com.example.shoppinglist.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.shoppinglist.data.model.Groceries
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromGroceriesJson(json: String): List<Groceries>{
        return jsonParser.fromJson<ArrayList<Groceries>>(
            json,
            object : TypeToken<ArrayList<Groceries>>(){}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toGroceriesJson(groceries: List<Groceries>): String{
        return jsonParser.toJson(
            groceries,
            object : TypeToken<ArrayList<Groceries>>(){}.type
        ) ?: "[]"
    }
}