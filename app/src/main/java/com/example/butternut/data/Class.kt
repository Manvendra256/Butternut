package com.example.butternut.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "classes")
data class Class(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val classId: Long = 0,
    @ColumnInfo(name = "name") val name: String,
)
