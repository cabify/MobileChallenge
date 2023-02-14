package com.cabify.demo.dependencyinjection

import com.cabify.demo.data.service.ProductLocalDataSource
import com.cabify.demo.data.service.ProductLocalDataSourceImpl
import com.cabify.demo.data.service.ProductRemoteDataSource
import com.cabify.demo.data.service.ProductRemoteDataSourceImpl
import org.koin.dsl.module

val servicesModules = module {
    single<ProductLocalDataSource> { ProductLocalDataSourceImpl() }
    single<ProductRemoteDataSource> { ProductRemoteDataSourceImpl() }
}