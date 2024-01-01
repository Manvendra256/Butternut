package com.example.butternut.data

import kotlinx.coroutines.flow.Flow

interface ClassesRepository {
    fun getClasses(): Flow<List<Class>>

    suspend fun listAll(): List<Class>

    suspend fun insertAll(classes: List<Class>)

    suspend fun deleteAll()

    suspend fun insertClass(class_: Class) : Long

    suspend fun deleteClass(class_: Class)

    suspend fun updateClass(class_: Class)
}