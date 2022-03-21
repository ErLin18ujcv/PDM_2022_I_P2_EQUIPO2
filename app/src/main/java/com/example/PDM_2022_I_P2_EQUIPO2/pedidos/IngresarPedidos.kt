package com.example.PDM_2022_I_P2_EQUIPO2.pedidos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.core.view.isEmpty
import com.example.PDM_2022_I_P2_EQUIPO2.R
import com.example.PDM_2022_I_P2_EQUIPO2.clases.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_ingresar_pedidos.*
import kotlinx.android.synthetic.main.ingresar_pedido_content.*
import java.util.HashMap

class IngresarPedidos : AppCompatActivity() {
    var clientesRegistrados : HashMap<Int, Cliente> = hashMapOf()
    var empleadosRegistrados : HashMap<Int, Empleado> = hashMapOf()
    var facturasRegistradas : HashMap<Int, Factura> = hashMapOf()
    var menusRegistrados : HashMap<Int, Menu> = hashMapOf()
    var pedidosRegistrados : HashMap<Int, Pedido> = hashMapOf()

    var detallesMenu = ArrayList<DetallePedido>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_pedidos)
        iniciarClientes()
        iniciarEmpleados()
        iniciarFacturas()
        iniciarMenus()
        iniciarPedidos()

        regresarListaPedido.setOnClickListener{regresarLista()}


        //iniciar spinner de clientes
        val spinnerClientes = findViewById<Spinner>(R.id.spinnerClientes)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,clientesRegistrados.values.toTypedArray())
        spinnerClientes.adapter = adapter
//
        //inciar spinner de menus
        val spinnerMenus = findViewById<Spinner>(R.id.spinnerMenus)
        val adapterMenu = ArrayAdapter(this,android.R.layout.simple_spinner_item,menusRegistrados.values.toTypedArray())
        spinnerMenus.adapter = adapterMenu

        //iniciar spinner de meseros
        val spinnerMeseros = findViewById<Spinner>(R.id.spinnerMeseros)
        var arrayMeseros = ArrayList<Empleado>()
        for (item in empleadosRegistrados){
            if (item.value.Puesto.trim().toLowerCase().equals("mesero"))
                arrayMeseros.add(item.value)
        }
        val adapterMeseros = ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayMeseros)
        spinnerMeseros.adapter = adapterMeseros



    }

    private fun regresarLista(){
        val intent = Intent(this, PedidosActivity::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }

    private fun obtenerMenuSeleccionado() : Menu{
        val spinnerMenu = findViewById<Spinner>(R.id.spinnerMenus)
        val idMenu = spinnerMenu.selectedItem.toString().split(":")[1].split("|")[0].trim()
        return menusRegistrados.getValue(idMenu.toInt())
    }

    private fun obtenerClienteSeleccionado() : Cliente{
        val spinnerCliente = findViewById<Spinner>(R.id.spinnerClientes)
        val idCliente = spinnerCliente.selectedItem.toString().split(":")[1].split("|")[0].trim()
        println("HOLA $idCliente")
        return clientesRegistrados.getValue(idCliente.toInt())
    }

    private fun obtenerEmpleadoSeleccionado() : Empleado{
        val spinnerEmpleado = findViewById<Spinner>(R.id.spinnerMeseros)
        val idEmpleado = spinnerEmpleado.selectedItem.toString().split(":")[1].split("|")[0].trim()
        return empleadosRegistrados.getValue(idEmpleado.toInt())
    }

    fun agregarDetalle(view: View){

        var cantidad = findViewById<EditText>(R.id.cantidadTxt)

        if (cantidad.text.equals("")){
            Snackbar.make(view,"Debes ingresar una cantidad.",Snackbar.LENGTH_SHORT).show()
            return
        }else if (cantidad.text.toString().toInt() < 1){
            Snackbar.make(view,"La cantidad debe ser mayor a 0.",Snackbar.LENGTH_SHORT).show()
            return
        }

        detallesMenu.add(
            DetallePedido(
                cantidad.text.toString().toInt(),
                obtenerMenuSeleccionado()
            )
        )

        //anadir menus listview
        var listView = findViewById<ListView>(R.id.menusPedidosList)
        var detallesString = ArrayList<String>()
        for (item in detallesMenu){
            var subtotal = item.Menu.Precio * item.cantidad
            detallesString.add("${item.Menu.Codigo} \t\t ${item.Menu.Nombre} \t ${item.Menu.Precio} \t ${item.cantidad} \t $subtotal")
        }
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,detallesString)
        listView.adapter = arrayAdapter

        totalPagarTxt.text = calcularTotal().toString()
    }

    fun guardarPedido(view : View){

        if (!verificarCamposEnBlanco()){
            Snackbar.make(view,"Debes llenar todos los campos",Snackbar.LENGTH_SHORT).show()
            return
        }

        var detallesString = ""
        for (i in 0 until detallesMenu.size){
            if (i == detallesMenu.size){
                detallesString += "${detallesMenu[i].Menu.Codigo},${detallesMenu[i].Menu.Nombre},${detallesMenu[i].Menu.Precio},${detallesMenu[i].Menu.Descripcion},${detallesMenu[i].cantidad}"
            }
            detallesString += "${detallesMenu[i].Menu.Codigo},${detallesMenu[i].Menu.Nombre},${detallesMenu[i].Menu.Precio},${detallesMenu[i].Menu.Descripcion},${detallesMenu[i].cantidad}|"
        }

        var pedidoGuardar = Pedido(pedidosRegistrados.size+1,obtenerClienteSeleccionado(),detallesString,obtenerEmpleadoSeleccionado(),calcularTotal())
        pedidosRegistrados.put(pedidoGuardar.id,pedidoGuardar)

        val intent = Intent(this, PedidosActivity::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()

    }

    private fun calcularTotal() : Double{
        var total = 0.0
        for (item in detallesMenu){
            total += (item.cantidad * item.Menu.Precio)
        }
        return total
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
        var cliente = findViewById<Spinner>(R.id.spinnerClientes)
        var menu = findViewById<Spinner>(R.id.spinnerMenus)
        var mesero = findViewById<Spinner>(R.id.spinnerMeseros)

        if (cliente.isEmpty() || menu.isEmpty() || mesero.isEmpty() || detallesMenu.isEmpty()){
            return false
        }
        return true
    }
}