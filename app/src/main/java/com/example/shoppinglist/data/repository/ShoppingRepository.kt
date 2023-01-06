package com.example.shoppinglist.data.repository

import com.example.shoppinglist.data.model.DataList

interface ShoppingRepository {

    suspend fun insertList(dataListEntity: DataList)

    suspend fun getAllList(): List<DataList>

    suspend fun getList(id: Int): DataList?

    suspend fun delete(dataListEntity: DataList)
}