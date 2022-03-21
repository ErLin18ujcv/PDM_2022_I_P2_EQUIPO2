package com.example.PDM_2022_I_P2_EQUIPO2.menus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.PDM_2022_I_P2_EQUIPO2.R
import com.example.PDM_2022_I_P2_EQUIPO2.clases.*
import com.example.PDM_2022_I_P2_EQUIPO2.clientes.ActivityClientes
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_ingresar_menu.*
import kotlinx.android.synthetic.main.ingresar_menu_content.*
import java.util.HashMap
import java.util.regex.Pattern

class IngresarMenu : AppCompatActivity() {
    var clientesRegistrados : HashMap<Int, Cliente> = hashMapOf()
    var empleadosRegistrados : HashMap<Int, Empleado> = hashMapOf()
    var facturasRegistradas : HashMap<Int, Factura> = hashMapOf()
    var menusRegistrados : HashMap<Int, Menu> = hashMapOf()
    var pedidosRegistrados : HashMap<Int, Pedido> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_menu)
        iniciarClientes()
        iniciarEmpleados()
        iniciarFacturas()
        iniciarMenus()
        iniciarPedidos()

        regresarListaMenu.setOnClickListener {
            regresarLista()
        }

        codigoMenuTxt.setText((menusRegistrados.size + 1).toString())

    }

    private fun regresarLista(){
        val intent = Intent(this, ActivityClientes::class.java)
        intent.putExtra("clientes", clientesRegistrados)
        intent.putExtra("empleados", empleadosRegistrados)
        intent.putExtra("facturas", facturasRegistradas)
        intent.putExtra("menus", menusRegistrados)
        intent.putExtra("pedidos", pedidosRegistrados)
        startActivity(intent)
        finish()
    }

    fun guardarMenu(view : View){

        if (!verificarCamposEnBlanco()){
            Snackbar.make(view,"Debes llenar todos los campos.",Snackbar.LENGTH_SHORT).show()
            return
        }

        var codigo = findViewById<EditText>(R.id.codigoMenuTxt).text.toString().toInt()
        var nombre = findViewById<EditText>(R.id.nombreMenuTxt).text.toString()
        var precio = findViewById<EditText>(R.id.precioMenuTxt).text.toString().toDouble()
        var descripcion = findViewById<EditText>(R.id.descripcionMenuTxt).text.toString()

        //validar nombre
        if (!verificarNombreMenu(nombre)){
            Snackbar.make(view,"Nombre de mení inválido.",Snackbar.LENGTH_SHORT).show()
            return
        }else if (nombre.length < 3){
            Snackbar.make(view,"Ese nombre es muy corto.",Snackbar.LENGTH_SHORT).show()
            return
        }else if (nombre.length > 15){
            Snackbar.make(view,"Ese nombre es muy largo",Snackbar.LENGTH_SHORT).show()
            return
        }

        //validar precio
        if (precio < 1){
            Snackbar.make(view,"El precio debe ser mayor a 0",Snackbar.LENGTH_SHORT).show()
            return
        }

        //validar descripcion
        if (!verificarDescripcion(descripcion)){
            Snackbar.make(view,"Descripcion Inválida",Snackbar.LENGTH_SHORT).show()
            return
        }else if (descripcion.length < 8){
            Snackbar.make(view,"Esa descripción es muy corta.",Snackbar.LENGTH_SHORT).show()
            return
        }else if(descripcion.length > 30){
            Snackbar.make(view,"Esa descripcion es muy larga.",Snackbar.LENGTH_SHORT).show()
            return
        }

        var menuNuevo = Menu(codigo,nombre,precio,descripcion)
        menusRegistrados.put(menuNuevo.Codigo,menuNuevo)
        Snackbar.make(view,"Menú Registrado", Snackbar.LENGTH_SHORT).show()

        val intent = Intent(this, MenusActivity::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }

    fun iniciarClientes(){
        if (intent.extras != null){
            val intent = intent
            clientesRegistrados = intent.getSerializableExtra("clientes") as HashMap<Int, Cliente>
        }
    }

    fun iniciarEmpleados(){
        if (intent.extras != null){
            val intent = intent
            empleadosRegistrados = intent.getSerializableExtra("empleados") as HashMap<Int, Empleado>
        }
    }

    fun iniciarFacturas(){
        if (intent.extras != null){
            val intent = intent
            facturasRegistradas = intent.getSerializableExtra("facturas") as HashMap<Int, Factura>
        }

    }

    fun iniciarMenus(){
        if (intent.extras != null){
            val intent = intent
            menusRegistrados = intent.getSerializableExtra("menus") as HashMap<Int, Menu>
        }

    }

    fun iniciarPedidos(){
        if (intent.extras != null){
            val intent = intent
            pedidosRegistrados = intent.getSerializableExtra("pedidos") as HashMap<Int, Pedido>
        }

    }

    private fun verificarCamposEnBlanco() : Boolean{
        var codigo = findViewById<EditText>(R.id.codigoMenuTxt)
        var nombre = findViewById<EditText>(R.id.nombreMenuTxt)
        var precio = findViewById<EditText>(R.id.precioMenuTxt)
        var descripcion = findViewById<EditText>(R.id.descripcionMenuTxt)

        if (codigo.text.isEmpty() || nombre.text.isEmpty() || precio.text.isEmpty() || descripcion.text.isEmpty()){
            return false
        }
        return true
    }

    private fun verificarNombreMenu(nombre : String) : Boolean{
        val pattern = "^\\b(?!.*?\\s{2})[A-Za-záéíóÁÉÍÓÚ ]*\\b\$"

        return Pattern.matches(pattern,nombre)
    }

    private fun verificarDescripcion(descripcion : String):Boolean{
        val pattern = "^\\b(?!.*?\\s{2})[A-Za-záéíóÁÉÍÓÚ ]*\\b\$"

        return Pattern.matches(pattern,descripcion)
    }


}