package com.example.shoppinglist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datalist")
data class DataList(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val color: Int,
    val lists: List<Groceries>,
    val percent: Float
)