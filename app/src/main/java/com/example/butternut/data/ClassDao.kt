package com.example.butternut.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    @Query("SELECT * FROM classes ORDER BY name")
    fun getClasses(): Flow<List<Class>>

    @Query("SELECT * FROM classes")
    suspend fun listAll(): List<Class>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(classes: List<Class>)

    @Query("DELETE FROM classes")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(class_ :Class) : Long

    @Delete
    suspend fun deleteClass(class_: Class)

    @Update
    suspend fun updateClass(class_ : Class)
}