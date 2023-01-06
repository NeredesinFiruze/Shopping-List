package com.example.shoppinglist.data.services

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.data.model.DataList

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(dataListEntity: DataList)

    @Query("SELECT * FROM datalist")
    suspend fun getAllList(): List<DataList>

    @Query("SELECT * FROM datalist WHERE id= :id")
    suspend fun getList(id: Int): DataList?

    @Delete
    suspend fun delete(dataListEntity: DataList)
}