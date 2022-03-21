package com.example.PDM_2022_I_P2_EQUIPO2.facturas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.core.view.isEmpty
import com.example.PDM_2022_I_P2_EQUIPO2.R
import com.example.PDM_2022_I_P2_EQUIPO2.clases.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_ingresar_factura.*
import kotlinx.android.synthetic.main.ingresar_factura_content.*
import java.util.HashMap

class IngresarFactura : AppCompatActivity() {
    var clientesRegistrados : HashMap<Int, Cliente> = hashMapOf()
    var empleadosRegistrados : HashMap<Int, Empleado> = hashMapOf()
    var facturasRegistradas : HashMap<Int, Factura> = hashMapOf()
    var menusRegistrados : HashMap<Int, Menu> = hashMapOf()
    var pedidosRegistrados : HashMap<Int, Pedido> = hashMapOf()

    var detalles = ArrayList<DetallePedido>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_factura)
        iniciarClientes()
        iniciarEmpleados()
        iniciarFacturas()
        iniciarMenus()
        iniciarPedidos()

        regresarListaFactura.setOnClickListener{
            regresarLista()
        }

        //iniciar spinner de pedidos
        val spinnerPedidos = findViewById<Spinner>(R.id.spinnerPedidos)
        val adapterPedidos = ArrayAdapter(this,android.R.layout.simple_spinner_item,pedidosRegistrados.values.toTypedArray())
        spinnerPedidos.adapter = adapterPedidos
        spinnerPedidos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                cargarDetalles(obtenerPedidoSeleccionado(spinnerPedidos.getItemAtPosition(p2).toString()))
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }


        }

        //iniciar spinner de tipos de  pago
        val spinnerTipoPago = findViewById<Spinner>(R.id.spinnerTipoPago)
        val arrayPagos = arrayOf("Efectivo","Tarjeta","Mixto")
        val adapterPagos = ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayPagos)
        spinnerTipoPago.adapter = adapterPagos
        spinnerTipoPago.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                montoTarjetaTxt.isEnabled = spinnerTipoPago.selectedItem.toString().equals("Mixto")
            }
        }

        //iniciar spinner de cajeros
        val spinnerCajeros = findViewById<Spinner>(R.id.spinnerCajeros)
        var arrayCajeros = ArrayList<Empleado>()
        for (item in empleadosRegistrados){
            if (item.value.Puesto.trim().toLowerCase().equals("cajero"))
                arrayCajeros.add(item.value)
        }
        val adapterMeseros = ArrayAdapter(this,android.R.layout.simple_spinner_item,arrayCajeros)
        spinnerCajeros.adapter = adapterMeseros

    }

    private fun regresarLista(){
        val intent = Intent(this, FacturasActivity::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }

    private fun stringToDetallePedido(detalle : String) : ArrayList<DetallePedido>{
        var arrayInfo = detalle.split("|")
        var arrayDetalles = ArrayList<DetallePedido>()
        for (i in 0 until arrayInfo.size-1){
            var codigo = arrayInfo[i].trim().split(",")[0].trim().toInt()
            var nombre = arrayInfo[i].trim().split(",")[1].trim()
            var precio = arrayInfo[i].trim().split(",")[2].toDouble()
            var descripcion = arrayInfo[i].trim().split(",")[3].trim()
            arrayDetalles.add(DetallePedido(arrayInfo[i].split(",")[4].toInt(),Menu(codigo,nombre,precio,descripcion)))
        }

        return arrayDetalles
    }

    private fun obtenerPedidoSeleccionado(pedidoSpinnerString : String): Pedido{
        val idPedido = pedidoSpinnerString.split(":")[1].split("|")[0].trim()
        return pedidosRegistrados.getValue(idPedido.toInt())
    }

    private fun obtenerCajeroSeleccionado() : Empleado{
        val spinnerCajeros = findViewById<Spinner>(R.id.spinnerCajeros)
        val idCajero = spinnerCajeros.selectedItem.toString().split(":")[1].split("|")[0].trim()
        return empleadosRegistrados.getValue(idCajero.toInt())
    }



    private fun cargarDetalles(pedido : Pedido){
        detalles = stringToDetallePedido(pedido.detallesPedido)
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,stringToDetallePedido(pedido.detallesPedido))
        listaDetalles.adapter = arrayAdapter
        totalPagarTxt.text = pedido.Total.toString()
    }

     fun guardarFactura(view : View){

         if (!verificarCamposEnBlanco()){
             Snackbar.make(view,"Debes llenar todos los campos.",Snackbar.LENGTH_SHORT).show()
             return
         }

         if (spinnerTipoPago.selectedItem.toString().equals("Mixto")) {
             if (montoTarjetaTxt.text.isEmpty()) {
                 Snackbar.make(
                     view,
                     "Debes especificar un monto de tarjeta.",
                     Snackbar.LENGTH_SHORT
                 ).show()
                 return
             } else if (!verificarTipoPago()) {
                 Snackbar.make(
                     view,
                     "El monto de tarjeta debe ser mayor a 0 y menor que el total.",
                     Snackbar.LENGTH_SHORT
                 ).show()
                 return
             }
         }

        var facturaGuardar = Factura(facturasRegistradas.size+1,
            obtenerCajeroSeleccionado(),
            spinnerTipoPago.selectedItem.toString(),
            obtenerPedidoSeleccionado(spinnerPedidos.selectedItem.toString()))

         var detalleString = ""
         for (item in detalles){
             detalleString += "$item \n"
         }

         println(facturaGuardar.id)
         facturasRegistradas.put(facturaGuardar.id,facturaGuardar)

         var factura = "________________Factura________________ \n" +
                       "Cliente: ${facturaGuardar.Pedido.Cliente.Nombre} \n" +
                       "Con Pedido N°: ${facturaGuardar.Pedido.id} \n" +
                       "Mesero: ${facturaGuardar.Pedido.Mesero.Nombre} \n" +
                       "Tipo de Pago: ${facturaGuardar.TipoPago} \n" +
                       "**************************************\n" +
                       "Id    Nombre Precio  Cantidad   Subtotal\n" +
                       detalleString +
                       "************************************** \n" +
                       "Total:                            ${facturaGuardar.Pedido.Total} \n"+
                       "Cajero: ${facturaGuardar.Cajero.Nombre} \n" +
                       "¡Gracias por su Compra!"


        //enviar correo
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(facturaGuardar.Pedido.Cliente.Correo))
        intent.putExtra(Intent.EXTRA_SUBJECT, "FACTURA DE PEDIDO")
        intent.putExtra(Intent.EXTRA_TEXT, factura)
        intent.type = "message/rfc822"
         startActivityForResult(Intent.createChooser(intent, "Cliente de Email"), 800)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 800) {
            //Called when returning from your email intent
        }
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

    //validaciones

    private fun verificarCamposEnBlanco() : Boolean{
        var spinnerPedidos = findViewById<Spinner>(R.id.spinnerPedidos)
        var spinnerCajero = findViewById<Spinner>(R.id.spinnerCajeros)

        if (spinnerPedidos.isEmpty() || spinnerCajero.isEmpty())
            return false

        return true
    }

    private fun verificarTipoPago() : Boolean{
        var spinnerTiposPago = findViewById<Spinner>(R.id.spinnerTipoPago)
        var monto = findViewById<EditText>(R.id.montoTarjetaTxt)
        println(spinnerTiposPago.selectedItem.toString().trim().toLowerCase())
        if (spinnerTiposPago.selectedItem.toString().trim().toLowerCase().equals("mixto")){

            if (monto.text.toString().toDouble() < 1.0 || monto.text.toString().toDouble() >= obtenerPedidoSeleccionado(spinnerPedidos.selectedItem.toString()).Total){
                return false
            }
        }
        return true
    }

    fun reiniciar(){
        val intent = Intent(this, IngresarFactura::class.java)
        intent.putExtra("clientes",clientesRegistrados)
        intent.putExtra("empleados",empleadosRegistrados)
        intent.putExtra("facturas",facturasRegistradas)
        intent.putExtra("menus",menusRegistrados)
        intent.putExtra("pedidos",pedidosRegistrados)
        startActivity(intent)
        finish()
    }
}