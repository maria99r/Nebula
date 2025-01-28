package com.alanturing.nebula.model.misTareas

class RepositorioMisTareas(private val daoMisTareas: DaoMisTareas) {
    fun getAll() = daoMisTareas.getAll()

    suspend fun insertarTarea(miTarea: MiTarea)
            = daoMisTareas.insertData(miTarea)

    suspend fun borrarTodasTareas(allMyFriends: List<MiTarea>)
            = daoMisTareas.deleteAllMyData(allMyFriends)

    suspend fun updateChecked(miTarea: MiTarea)
            = daoMisTareas.updateChecked(miTarea)

    suspend fun borrarTarea(miTarea: MiTarea)
            = daoMisTareas.deleteData(miTarea)

    fun getSelected() = daoMisTareas.getSelected()
}