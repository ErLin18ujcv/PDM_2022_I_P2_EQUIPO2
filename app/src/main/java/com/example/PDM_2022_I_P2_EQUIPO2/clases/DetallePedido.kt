package com.example.PDM_2022_I_P2_EQUIPO2.clases

import android.os.Parcel
import android.os.Parcelable

class DetallePedido() : Parcelable {
    var cantidad = 0
    var Menu = Menu()

    constructor(cantidad : Int, menu : Menu) : this(){
        this.cantidad = cantidad
        this.Menu = menu
    }

    constructor(parcel: Parcel) : this() {
        cantidad = parcel.readInt()
        Menu = parcel.readParcelable(com.example.PDM_2022_I_P2_EQUIPO2.clases.Menu::class.java.classLoader)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(cantidad)
        parcel.writeParcelable(Menu, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DetallePedido> {
        override fun createFromParcel(parcel: Parcel): DetallePedido {
            return DetallePedido(parcel)
        }

        override fun newArray(size: Int): Array<DetallePedido?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        var subtotalDetalle = cantidad * Menu.Precio
        return "${Menu.Codigo}    ${Menu.Nombre}     ${Menu.Precio}     $cantidad     $subtotalDetalle"
    }


}