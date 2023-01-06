package com.example.shoppinglist.di

import android.app.Application
import androidx.room.Room
import com.example.shoppinglist.util.GsonParser
import com.example.shoppinglist.data.repository.ShoppingRepository
import com.example.shoppinglist.data.repository.ShoppingRepositoryImpl
import com.example.shoppinglist.data.services.ShoppingDatabase
import com.example.shoppinglist.util.Converters
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): ShoppingDatabase{
        return Room
            .databaseBuilder(
                application,
                ShoppingDatabase::class.java,
                ShoppingDatabase.DATABASE_NAME
            )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: ShoppingDatabase): ShoppingRepository{
        return ShoppingRepositoryImpl(database.dao)
    }
}