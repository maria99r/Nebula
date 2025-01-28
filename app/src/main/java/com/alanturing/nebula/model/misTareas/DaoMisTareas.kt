package com.alanturing.nebula.model.misTareas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoMisTareas {
    @Query("SELECT * FROM sample")
    fun getAll(): Flow<List<MiTarea>>

    @Query("SELECT * FROM sample WHERE isChecked is 0")
    fun getSelected(): Flow<List<MiTarea>>

    @Insert
    suspend fun insertData(miTarea: MiTarea)

    @Delete
    suspend fun deleteAllMyData(allMyData: List<MiTarea>)

    @Delete
    suspend fun deleteData(miTarea: MiTarea)

    @Update
    suspend fun updateChecked(miTarea: MiTarea)
}