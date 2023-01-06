package com.example.shoppinglist.data.repository

import com.example.shoppinglist.data.model.DataList
import com.example.shoppinglist.data.services.ShoppingDao

class ShoppingRepositoryImpl(private val dao: ShoppingDao): ShoppingRepository {
    override suspend fun insertList(dataListEntity: DataList) {
        return dao.insertList(dataListEntity)
    }

    override suspend fun getAllList(): List<DataList> {
        return dao.getAllList()
    }

    override suspend fun getList(id: Int): DataList? {
        return dao.getList(id)
    }

    override suspend fun delete(dataListEntity: DataList) {
        return dao.delete(dataListEntity)
    }
}