package com.example.PDM_2022_I_P2_EQUIPO2.clases

import android.os.Parcel
import android.os.Parcelable

class Pedido() : Parcelable{
    var id = 0
    var Cliente = Cliente()
    var detallesPedido = ""
    var Mesero = Empleado()
    var Total = 0.0


    constructor(
        id: Int,
        Cliente: Cliente,
        detallesPedido: String,
        Mesero: Empleado,
        Total: Double
    ):this() {
        this.id = id
        this.Cliente = Cliente
        this.detallesPedido = detallesPedido
        this.Mesero = Mesero
        this.Total = Total
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        Cliente =
            parcel.readParcelable(com.example.PDM_2022_I_P2_EQUIPO2.clases.Cliente::class.java.classLoader)!!
        detallesPedido = parcel.readString().toString()
        Mesero = parcel.readParcelable(Empleado::class.java.classLoader)!!
        Total = parcel.readDouble()
    }


    override fun toString(): String {
        return "ID: $id | Cliente: ${Cliente.Nombre} | Total: $Total"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(Cliente, flags)
        parcel.writeString(detallesPedido)
        parcel.writeParcelable(Mesero, flags)
        parcel.writeDouble(Total)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pedido> {
        override fun createFromParcel(parcel: Parcel): Pedido {
            return Pedido(parcel)
        }

        override fun newArray(size: Int): Array<Pedido?> {
            return arrayOfNulls(size)
        }
    }


}

