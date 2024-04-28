package com.example.lista_tareas


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.lista_tareas.db.Compra
import com.example.lista_tareas.db.CompraDao
import com.example.lista_tareas.db.DbHelper
import com.example.lista_tareas.db.dbCompra
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Modifier
import androidx.compose.ui.tooling.preview.Preview as Preview1


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Operaciones de base de datos en un hilo de fondo al iniciar la actividad
        lifecycleScope.launch(Dispatchers.IO) {
            // Crear una instancia de DbHelper y CompraDao para acceder a la base de datos
            val dbHelper = DbHelper(this@MainActivity)
            val dao = CompraDao(dbHelper)

            // Insertar algunas compras de ejemplo en la base de datos
            dao.insertar(Compra(0, "Arroz", false))
            dao.insertar(Compra(0, "Aceite", false))
            dao.insertar(Compra(0, "Carne", false))
            dao.insertar(Compra(0, "Pollo", false))
            dao.insertar(Compra(0, "Bebida", false))
        }

        // Configuración de la interfaz de usuario utilizando Jetpack Compose
        setContent {
            // Mostrar la lista de compras en la UI
            ListaCompraUI()
        }
    }
}



@Composable

fun ListaCompraUI() {
    val contexto = LocalContext.current
    val (compra, setCompra) = remember{ mutableStateOf(emptyList<Compra>()) }
// Cargar las compras desde la base de datos en un hilo de fondo cuando 'compras'
    LaunchedEffect(compra) {
        withContext(Dispatchers.IO){
            val dbHelper  = DbHelper(contexto)
            val dao = CompraDao(dbHelper)
            setCompra(dao.finALL())
        }
    }
    LazyColumn(){
        modifier = Modifier.fillMaxSize()
        items(compra){compra->
            // Mostrar cada compra en un elemento de la lista
            Row(){
                Text(compra.id.toString())
                Text(compra.compra)
                Text(compra.realizada.toString())
                ListaCompraUI(compra)
                setCompra(emptyList<Compra>())
            }

        }
    }
    @Preview1
    @Composable

    fun CompraitemUI(compra: Compra , onSave:()-> Unit = {}){
        val contexto = LocalContext.current
        val alcance = rememberCoroutineScope()

        Row (modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp, horizontal = 20.dp)

               // Mostrar un ícono según si la compra está realizada o no
        ){
            if(compra.realizada){
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Compra Realizada",
                modifier = Modifier.clickable{
                    alcance.launch(Dispatchers.IO){
                     var dao =   dbCompra.getInstance(contexto).compraDao()
                     compra.realizada = false
                        dao.actualizar(compra)
                     onSave()
                    }
                }
                )
            }
              else{
                Icon(
                    Icons.Filled.Build,
                    contentDescription = "Compra No Realizada",
                            modifier = Modifier.clickable{
                        alcance.launch(Dispatchers.IO){
                            // Cambiar el estado de realización de la compra en la base de datos
                            var dao =   dbCompra.getInstance(contexto).compraDao()
                            compra.realizada = true
                            dao.actualizar(compra)
                            onSave()
                        }
                    }
                )
              }
            Spacer(modifier = Modifier.weight(2f))
            // Mostrar el nombre de la compra
            Text(
                text = compra.compra,

               )
            Icon(
                // Icono para eliminar la compra
                Icons.Filled.Delete,
                contentDescription = "Eliminar Compra",
                        modifier = Modifier.clickable{
                    alcance.launch(Dispatchers.IO){
                        // Eliminar la compra de la base de datos
                        var dao =   dbCompra.getInstance(contexto).compraDao()
                        compra.realizada = true
                        dao.eliminar(compra)
                        onSave()
                    }
                }
            )
            }
        }
    @Preview1(showBackground = true)
    @Composable

    fun CompraItemUiPreview(){
        val compra = Compra(1,"Arroz",true)
        CompraitemUI(compra)


    }
    @Preview1(showBackground = true)
    @Composable

    fun CompraItemUiPreview2(){
        val compra = Compra(1,"Arroz",true)
        CompraitemUI(compra)


    }
    }



