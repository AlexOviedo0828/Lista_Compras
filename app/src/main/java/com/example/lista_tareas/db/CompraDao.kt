package com.example.lista_tareas.db

import android.content.ContentValues


class CompraDao(val db:DbHelper) {
    //Método para obtener todas las compras de la base de datos
    fun finALL(): List<Compra> {
        // Consulta a la base de datos para obtener todas las compras
        val cursor = db.readableDatabase.query(
            dbCompra.TablaCompras.TABLA_NOMBRE,
            null, // Columnas a seleccionar (en este caso, todas)
            "", // Clausula WHERE (en este caso, ninguna)
            null, // Argumentos de la clausula WHERE (en este caso, ninguno)
            null, // Agrupamiento (en este caso, ninguno)
            null, // Clausula HAVING (en este caso, ninguna)
            "${dbCompra.TablaCompras.COLUMNA_REALIZADA} ASC"// Orden de los resultados (ascendente por la columna "realizada")
        ) // Lista para almacenar las compras obtenidas
        val lista = mutableListOf<Compra>()
        // Iterar sobre el cursor para obtener cada compra
        while (cursor.moveToNext()) {
            // Obtener el ID de la compra
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(dbCompra.TablaCompras.COLUMNA_ID))
            // Obtener la descripción de la compra
            val actividad =
                cursor.getString(cursor.getColumnIndexOrThrow(dbCompra.TablaCompras.COLUMNA_COMPRA))
            val realizada =
                cursor.getInt(cursor.getColumnIndexOrThrow(dbCompra.TablaCompras.COLUMNA_REALIZADA))
            val compra =
                Compra(id, actividad, realizada > 0)// Convertir el valor de realizada a booleano
            // Agregar la compra a la lista
            lista.add(compra)


        }// Cerrar el cursor para liberar recursos
        cursor.close()
        return lista// Devolver la lista de compras
    }

    class CompraDao(val db: DbHelper) {

        // Método para insertar una nueva compra en la base de datos
        fun insertar(compra: Compra): Long {
            // Crear un objeto ContentValues para almacenar los valores a insertar
            val valores = ContentValues().apply {
                // Asignar los valores de la compra a las columnas correspondientes
                put(dbCompra.TablaCompras.COLUMNA_COMPRA, compra.compra)
                put(dbCompra.TablaCompras.COLUMNA_REALIZADA, compra.realizada)
            }
            // Insertar los valores en la base de datos y devolver el ID de la nueva fila insertada
            return db.writableDatabase.insert(dbCompra.TablaCompras.TABLA_NOMBRE, null, valores)
        }

        // Método para actualizar una compra existente en la base de datos
        fun actualizar(compra: Compra): Int {
            // Crear un objeto ContentValues con los nuevos valores de la compra
            val valores = ContentValues().apply {
                put(dbCompra.TablaCompras.COLUMNA_COMPRA, compra.compra)
                put(dbCompra.TablaCompras.COLUMNA_REALIZADA, if (compra.realizada) 1 else 0)
            }
            // Definir la cláusula WHERE para identificar la compra a actualizar
            val whereClause = "${dbCompra.TablaCompras.COLUMNA_ID} = ?"
            val whereArgs = arrayOf(compra.id.toString())
            // Actualizar la compra en la base de datos y devolver el número de filas afectadas
            return db.writableDatabase.update(
                dbCompra.TablaCompras.TABLA_NOMBRE,
                valores,
                whereClause,
                whereArgs
            )
        }

        // Método para eliminar una compra de la base de datos
        fun eliminar(compraId: Int): Int {
            // Definir la cláusula WHERE para identificar la compra a eliminar
            val whereClause = "${dbCompra.TablaCompras.COLUMNA_ID} = ?"
            val whereArgs = arrayOf(compraId.toString())
            // Eliminar la compra de la base de datos y devolver el número de filas afectadas
            return db.writableDatabase.delete(
                dbCompra.TablaCompras.TABLA_NOMBRE,
                whereClause,
                whereArgs
            )
        }

        // Método para contar el número total de compras en la base de datos
        fun contar(): Int {
            // Construir la consulta SQL para contar las filas en la tabla de compras
            val query = "SELECT COUNT(*) FROM ${dbCompra.TablaCompras.TABLA_NOMBRE}"
            // Ejecutar la consulta y obtener el resultado como un cursor
            val cursor = db.readableDatabase.rawQuery(query, null)
            // Mover el cursor a la primera fila
            cursor.moveToFirst()
            // Obtener el valor del conteo de la primera columna del resultado
            val count = cursor.getInt(0)
            // Cerrar el cursor para liberar recursos
            cursor.close()
            // Devolver el conteo total de compras
            return count
        }
    }
}