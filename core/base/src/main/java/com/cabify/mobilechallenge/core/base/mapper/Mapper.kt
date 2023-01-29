package com.cabify.mobilechallenge.core.base.mapper

interface Mapper<in I, out O> {
    fun map(input: I): O
}