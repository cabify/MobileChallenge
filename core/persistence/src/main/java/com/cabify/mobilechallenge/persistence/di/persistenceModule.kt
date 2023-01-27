package com.cabify.mobilechallenge.persistence.di

import com.cabify.mobilechallenge.persistence.config.AppDatabase
import com.cabify.mobilechallenge.persistence.config.createDb
import com.cabify.mobilechallenge.persistence.dao.CartDAO
import com.cabify.mobilechallenge.persistence.entity.CartItemData
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val corePersistenceModule = module {
    single {
        createDb(applicationContext = androidContext())
    }
    single<CartDAO<CartItemData>> {
        get<AppDatabase>().cartDao()
    }
}