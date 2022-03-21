package com.example.PDM_2022_I_P2_EQUIPO2.empleados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.PDM_2022_I_P2_EQUIPO2.R
import com.example.PDM_2022_I_P2_EQUIPO2.clases.*
import com.example.PDM_2022_I_P2_EQUIPO2.clientes.ActivityClientes
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_registrar_empleado.*
import kotlinx.android.synthetic.main.ingresar_empleado_content.*
import java.util.HashMap
import java.util.regex.Pattern

class RegistrarEmpleado : AppCompatActivity() {
    var clientesRegistrados : HashMap<Int, Cliente> = hashMapOf()
    var empleadosRegistrados : HashMap<Int, Empleado> = hashMapOf()
    var facturasRegistradas : HashMap<Int, Factura> = hashMapOf()
    var menusRegistrados : HashMap<Int, Menu> = hashMapOf()
    var pedidosRegistrados : HashMap<Int, Pedido> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_empleado)
        iniciarClientes()
        iniciarEmpleados()
        iniciarFacturas()
        iniciarMenus()
        iniciarPedidos()

        regresarListaEmp.setOnClickListener {
            regresarLista()
        }

        codigoEmpleadoTxt.setText((empleadosRegistrados.size + 1).toString())
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

    fun guardarEmpleado(view : View){
        if (!verificarCamposEnBlanco()){
            Snackbar.make(view, "Debes llenar todos los campos.",Snackbar.LENGTH_SHORT).show()
            return
        }

        var codigo = findViewById<EditText>(R.id.codigoEmpleadoTxt).text.toString().toInt()
        var nombre = findViewById<EditText>(R.id.nombreEmpleadoTxt).text.toString()
        var puesto = findViewById<EditText>(R.id.puestoEmpleadoTxt).text.toString()

        if (!validacionNombreEmpleado(nombre)){
            Snackbar.make(view,"Nombre Inválido.",Snackbar.LENGTH_SHORT).show()
            return
        }else if (nombre.length < 3){
            Snackbar.make(view,"El nombre debe tener mas de 3 caracteres.",Snackbar.LENGTH_SHORT).show()
            return
        }else if (nombre.length > 30){
            Snackbar.make(view,"Ese nombre es muy largo.",Snackbar.LENGTH_SHORT).show()
            return
        }

        if (!validacionPuesto(puesto)){
            Snackbar.make(view,"Nombre de puesto inválido.",Snackbar.LENGTH_SHORT).show()
            return
        }else if (puesto.length < 3){
            Snackbar.make(view,"El puesto debe tener mas de 3 caracteres.",Snackbar.LENGTH_SHORT).show()
            return
        }else if (puesto.length > 20){
            Snackbar.make(view,"Ese puesto es muy largo.",Snackbar.LENGTH_SHORT).show()
            return
        }

        if (!validarGerenteUnico()){
            if (puesto.toLowerCase().equals("gerente")){
                Snackbar.make(view,"Ya existe un gerente.",Snackbar.LENGTH_SHORT).show()
                return
            }
        }

        var empleadoNuevo = Empleado(codigo,nombre,puesto)
        empleadosRegistrados.put(empleadoNuevo.Codigo,empleadoNuevo)
        Snackbar.make(view,"Empleado Registrado", Snackbar.LENGTH_SHORT).show()

        val intent = Intent(this, ActivityEmpleados::class.java)
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
        var codigo = findViewById<EditText>(R.id.codigoEmpleadoTxt)
        var nombre = findViewById<EditText>(R.id.nombreEmpleadoTxt)
        var puesto = findViewById<EditText>(R.id.puestoEmpleadoTxt)

        if (codigo.text.isEmpty() || nombre.text.isEmpty() || puesto.text.isEmpty()){
            return false
        }
        return true
    }

    private fun validacionNombreEmpleado(nombre : String) : Boolean{
        val pattern = "^([A-Z][a-záéíóú]* )(([A-Z][a-záéíóú]* [A-Z][a-záéíóú]*)|([A-Z][a-záéíóú]*)|([A-Z][a-záéíóú]* [A-Z][a-záéíóú]* [A-Z][a-záéíóú]*))\$"

        return Pattern.matches(pattern,nombre)
    }

    private fun validacionPuesto(puesto : String) : Boolean{
        val pattern = "^[A-Z][a-záéíóú]*\$"

        return Pattern.matches(pattern,puesto)
    }

    private fun validarGerenteUnico() : Boolean{

        for (item in empleadosRegistrados){
            if (item.value.Puesto.toLowerCase().equals("gerente")){
                return false
            }
        }
        return true
    }

}