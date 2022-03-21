package com.example.PDM_2022_I_P2_EQUIPO2.clases

import android.os.Parcel
import android.os.Parcelable


class Factura() :Parcelable {
    var id = 0
    var Cajero = Empleado()
    var TipoPago = ""
    var Pedido = Pedido()

    constructor(id : Int, Cajero: Empleado, TipoPago: String, Pedido: Pedido) : this() {
        this.id = id
        this.Cajero = Cajero
        this.TipoPago = TipoPago
        this.Pedido = Pedido
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        Cajero = parcel.readParcelable(Empleado::class.java.classLoader)!!
        TipoPago = parcel.readString().toString()
        Pedido = parcel.readParcelable(com.example.PDM_2022_I_P2_EQUIPO2.clases.Pedido::class.java.classLoader)!!
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(Cajero, flags)
        parcel.writeString(TipoPago)
        parcel.writeParcelable(Pedido, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Factura> {
        override fun createFromParcel(parcel: Parcel): Factura {
            return Factura(parcel)
        }

        override fun newArray(size: Int): Array<Factura?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "ID: $id | Cajero: ${Cajero.Nombre} | Tipo de Pago: $TipoPago | Total: ${Pedido.Total}"
    }

}