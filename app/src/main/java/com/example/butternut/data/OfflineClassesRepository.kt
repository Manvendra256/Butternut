package com.example.butternut.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineClassesRepository @Inject constructor(private val classDao: ClassDao) :
    ClassesRepository {
    override fun getClasses(): Flow<List<Class>> = classDao.getClasses()
    override suspend fun insertAll(classes: List<Class>) = withContext(Dispatchers.IO){ classDao.insertAll(classes)}
    override suspend fun deleteAll()  = withContext(Dispatchers.IO) {classDao.deleteAll()}
    override suspend fun listAll(): List<Class> = withContext(Dispatchers.IO) {classDao.listAll()}
    override suspend fun insertClass(class_: Class): Long =
        withContext(Dispatchers.IO) { classDao.insertClass(class_) }
    override suspend fun deleteClass(class_: Class) =
        withContext(Dispatchers.IO) { classDao.deleteClass(class_) }
    override suspend fun updateClass(class_: Class) =
        withContext(Dispatchers.IO) { classDao. updateClass(class_) }
}