package com.example.PDM_2022_I_P2_EQUIPO2.pedidos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.PDM_2022_I_P2_EQUIPO2.MainActivity
import com.example.PDM_2022_I_P2_EQUIPO2.R
import com.example.PDM_2022_I_P2_EQUIPO2.clases.*
import kotlinx.android.synthetic.main.activity_pedidos.*
import java.util.ArrayList
import java.util.HashMap

class PedidosActivity : AppCompatActivity() {

    var clientesRegistrados : HashMap<Int, Cliente> = hashMapOf()
    var empleadosRegistrados : HashMap<Int, Empleado> = hashMapOf()
    var facturasRegistradas : HashMap<Int, Factura> = hashMapOf()
    var menusRegistrados : HashMap<Int, Menu> = hashMapOf()
    var pedidosRegistrados : HashMap<Int, Pedido> = hashMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedidos)
        iniciarClientes()
        iniciarEmpleados()
        iniciarFacturas()
        iniciarMenus()
        iniciarPedidos()

        nuevoPedidoBtn.setOnClickListener {
            ingresarPedido()
        }

        regresarPedido.setOnClickListener {
            menuPrincipal()
        }

        //llenar list view
        var array = ArrayList<Pedido>()
        for (item in pedidosRegistrados){
            val pedido = item.value
            array.add(pedido)
        }

        var listAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, array)
        listaPedidos.adapter = listAdapter
    }

    fun ingresarPedido(){
        val intent = Intent(this, IngresarPedidos::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }

    fun menuPrincipal(){
        val intent = Intent(this, MainActivity::class.java)
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
            for (item in pedidosRegistrados){
            }
        }

    }
}