package com.example.PDM_2022_I_P2_EQUIPO2.clientes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.PDM_2022_I_P2_EQUIPO2.R
import com.example.PDM_2022_I_P2_EQUIPO2.clases.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_ingresar_cliente.*
import kotlinx.android.synthetic.main.ingresar_cliente_content.*
import java.util.*
import java.util.regex.Pattern


class IngresarCliente : AppCompatActivity() {
    var clientesRegistrados : HashMap<Int, Cliente> = hashMapOf()
    var empleadosRegistrados : HashMap<Int, Empleado> = hashMapOf()
    var facturasRegistradas : HashMap<Int, Factura> = hashMapOf()
    var menusRegistrados : HashMap<Int, Menu> = hashMapOf()
    var pedidosRegistrados : HashMap<Int, Pedido> = hashMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_cliente)
        iniciarClientes()
        iniciarEmpleados()
        iniciarFacturas()
        iniciarMenus()
        iniciarPedidos()

        regresarLista.setOnClickListener {
            regresarLista()
        }


        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        codigoClienteTxt.setText((clientesRegistrados.size + 1).toString())

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

     fun guardarCliente(view: View){

         //validar campos en blanco
         if (!verificarCamposEnBlanco()){
             Snackbar.make(view, "Debes llenar todos los campos.", Snackbar.LENGTH_SHORT).show()
             return
         }

        var codigo = findViewById<EditText>(R.id.codigoClienteTxt).text.toString()
        var nombre = findViewById<EditText>(R.id.nombreClienteTxt).text.toString()
        var correo = findViewById<EditText>(R.id.correoClienteTxt).text.toString()

         if (!validacionNombreCliente(nombre)){
             Snackbar.make(view, "Nombre Inválido.", Snackbar.LENGTH_SHORT).show()
             return
         }else if (nombre.length < 3){
             Snackbar.make(view, "El nombre debe tener mas de 3 caracteres.", Snackbar.LENGTH_SHORT).show()
             return
         }else if (nombre.length > 30){
             Snackbar.make(view, "Ese nombre es muy largo.", Snackbar.LENGTH_SHORT).show()
             return
         }

         if (!validacionCorreoCliente(correo)){
             Snackbar.make(view, "Correo Inválido.", Snackbar.LENGTH_SHORT).show()
             return
         }

        var clienteNuevo = Cliente(Integer.parseInt(codigo), nombre, correo)
        clientesRegistrados.put(clienteNuevo.Id, clienteNuevo)
        Snackbar.make(view, "Cliente Registrado", Snackbar.LENGTH_SHORT).show()

         val intent = Intent(this, ActivityClientes::class.java)
         intent.putExtra("clientes", clientesRegistrados)
         intent.putExtra("empleados", empleadosRegistrados)
         intent.putExtra("facturas", facturasRegistradas)
         intent.putExtra("menus", menusRegistrados)
         intent.putExtra("pedidos", pedidosRegistrados)
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
        var codigo = findViewById<EditText>(R.id.codigoClienteTxt)
        var nombre = findViewById<EditText>(R.id.nombreClienteTxt)
        var correo = findViewById<EditText>(R.id.correoClienteTxt)

        if (codigo.text.isEmpty() || nombre.text.isEmpty() || correo.text.isEmpty()){
            return false
        }
        return true
    }

    private fun validacionNombreCliente(nombre: String) : Boolean{
        val pattern = "^([A-Z][a-záéíóú]* )(([A-Z][a-záéíóú]* [A-Z][a-záéíóú]*)|([A-Z][a-záéíóú]*)|([A-Z][a-záéíóú]* [A-Z][a-záéíóú]* [A-Z][a-záéíóú]*))\$"

        return Pattern.matches(pattern, nombre)
    }

    private fun validacionCorreoCliente(correo: String) : Boolean{
        val pattern = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})\$"

        return Pattern.matches(pattern, correo)
    }


}