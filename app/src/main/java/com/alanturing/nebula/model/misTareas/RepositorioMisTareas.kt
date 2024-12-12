package com.alanturing.nebula.model.misTareas

class RepositorioMisTareas(private val daoMisTareas: DaoMisTareas) {
    fun getAll() = daoMisTareas.getAll()

    suspend fun insertarTarea(miTarea: MiTarea)
            = daoMisTareas.insertData(miTarea)

    suspend fun borrarTodasTareas(allMyFriends: List<MiTarea>)
            = daoMisTareas.deleteAllMyData(allMyFriends)
}