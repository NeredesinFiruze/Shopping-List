package com.example.shoppinglist.data.services

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shoppinglist.data.model.DataList
import com.example.shoppinglist.util.Converters

@Database(entities = [DataList::class], version = 2)
@TypeConverters(Converters::class)
abstract class ShoppingDatabase : RoomDatabase(){

    abstract val dao: ShoppingDao

    companion object{
        const val DATABASE_NAME = "database_shopping"
    }
}