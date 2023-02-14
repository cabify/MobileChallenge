package com.cabify.demo.data.service

import com.cabify.demo.data.model.ResponseApi

interface ProductLocalDataSource {

    fun getProducts(): ResponseApi
}