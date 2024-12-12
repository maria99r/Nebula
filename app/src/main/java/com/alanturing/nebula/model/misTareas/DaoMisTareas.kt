package com.alanturing.nebula.model.misTareas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoMisTareas {
    @Query("SELECT * FROM sample")
    fun getAll(): Flow<List<MiTarea>>

    @Insert
    suspend fun insertData(miTarea: MiTarea)

    @Delete
    suspend fun deleteAllMyData(allMyData: List<MiTarea>)
}