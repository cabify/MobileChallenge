package com.cabify.mobilechallenge.persistence.config

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cabify.mobilechallenge.persistence.dao.CartDAORoom
import com.cabify.mobilechallenge.persistence.entity.CartItemData

private const val DB_VERSION = 1
private const val DB_NAME = "cart-items-db"

@Database(entities = [CartItemData::class], version = DB_VERSION)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDAORoom
}

internal fun createDb(applicationContext: Context): AppDatabase =
    Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, DB_NAME
    ).build()