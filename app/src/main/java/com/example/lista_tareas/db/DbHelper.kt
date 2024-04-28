package com.example.lista_tareas.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(contexto: Context) : SQLiteOpenHelper(contexto, DB_NOMBRE, null, DB_VERSION) {

    companion object {
        // Definir el nombre y la versión de la base de datos, así como la sentencia SQL para crear la tabla de compras
        const val DB_NOMBRE = "Compra.Db"
        const val DB_VERSION = 1
        const val SQL_CREACION_TABLAS =
            """CREATE TABLE ${dbCompra.TablaCompras.TABLA_NOMBRE} (
            ${dbCompra.TablaCompras.COLUMNA_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${dbCompra.TablaCompras.COLUMNA_COMPRA} TEXT,
            ${dbCompra.TablaCompras.COLUMNA_REALIZADA} INTEGER
            )"""
    }

    // Método llamado cuando se crea la base de datos por primera vez
    override fun onCreate(db: SQLiteDatabase?) {
        // Ejecutar la sentencia SQL para crear la tabla de compras
        db?.execSQL(SQL_CREACION_TABLAS)
    }

    // Método llamado cuando se necesita actualizar la base de datos
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}

