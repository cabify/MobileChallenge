package com.cabify.demo.data.service

import com.cabify.demo.data.model.ResponseApi

interface ProductDataSource {
    suspend fun getProducts(): ResponseApi
}