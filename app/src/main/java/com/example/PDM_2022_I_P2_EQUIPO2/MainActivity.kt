package com.example.PDM_2022_I_P2_EQUIPO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.PDM_2022_I_P2_EQUIPO2.clases.*
import com.example.PDM_2022_I_P2_EQUIPO2.clientes.ActivityClientes
import com.example.PDM_2022_I_P2_EQUIPO2.empleados.ActivityEmpleados
import com.example.PDM_2022_I_P2_EQUIPO2.facturas.FacturasActivity
import com.example.PDM_2022_I_P2_EQUIPO2.menus.MenusActivity
import com.example.PDM_2022_I_P2_EQUIPO2.pedidos.PedidosActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainActivity : AppCompatActivity() {
    var clientesRegistrados : HashMap<Int,Cliente> = hashMapOf()
    var empleadosRegistrados : HashMap<Int, Empleado> = hashMapOf()
    var facturasRegistradas : HashMap<Int, Factura>  = hashMapOf()
    var menusRegistrados : HashMap<Int, Menu> = hashMapOf()
    var pedidosRegistrados : HashMap<Int, Pedido> = hashMapOf()
    //PDM_2022_I_P2_EQUIPO#2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ocultar botones
        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        iniciarClientes()
        iniciarEmpleados()
        iniciarFacturas()
        iniciarMenus()
        iniciarPedidos()

        cardViewClientes.setOnClickListener {
            clientes()
        }

        cardViewEmpleados.setOnClickListener {
            empleados()
        }

        cardViewFacturas.setOnClickListener {
            facturas()
        }

        cardViewMenus.setOnClickListener {
            menus()
        }

        cardViewPedidos.setOnClickListener {
            pedidos()
        }

        //establecerCounts()

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

    private fun clientes(){
        val intent = Intent(this,ActivityClientes::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }

    private fun empleados(){
        val intent = Intent(this,ActivityEmpleados::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }

    private fun facturas(){
        val intent = Intent(this,FacturasActivity::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }

    private fun menus(){
        val intent = Intent(this,MenusActivity::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }

    private fun pedidos(){
        val intent = Intent(this,PedidosActivity::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }

//    private fun establecerCounts(){
//        clientesCount.text = clientesRegistrados.size.toString()
//        menusCount.text = menusRegistrados.size.toString()
//        empleadosCount.text = empleadosRegistrados.size.toString()
//        pedidosCount.text = pedidosRegistrados.size.toString()
//        facturasCount.text = facturasRegistradas.size.toString()
//    }





}