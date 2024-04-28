package com.example.lista_tareas.db

data class Compra(
    val id: Int, // ID de la compra
    var compra: String, // Nombre de la compra
    var realizada: Boolean // Estado de realización de la compra
)
