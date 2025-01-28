package com.alanturing.nebula.model.misTareas

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MiTarea::class], version = 1)
abstract class BaseDatosMisTareas: RoomDatabase() {
    abstract fun myDataDao(): DaoMisTareas

    companion object {
        @Volatile
        private var Instance: BaseDatosMisTareas? = null

        fun getMyDatabase(context: Context): BaseDatosMisTareas {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = BaseDatosMisTareas::class.java,
                    name = "tareas",
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}