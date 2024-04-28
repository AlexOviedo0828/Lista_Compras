package com.example.lista_tareas.db

object dbCompra {
    // Definición de un objeto que representa la base de datos de compras

    object TablaCompras {
        // Definición de un objeto que representa la tabla de compras en la base de datos
        const val TABLA_NOMBRE = "compras"
        // Nombre de la tabla de compras en la base de datos
        const val COLUMNA_ID = "id"
        // Nombre de la columna de ID en la tabla de compras
        const val COLUMNA_COMPRA = "compra"
        // Nombre de la columna de nombre de compra en la tabla de compras
        const val COLUMNA_REALIZADA = "realizada"
        // Nombre de la columna de estado de realización en la tabla de compras
    }
}
