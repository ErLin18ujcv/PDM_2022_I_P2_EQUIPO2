package com.example.PDM_2022_I_P2_EQUIPO2.clases

import android.os.Parcel
import android.os.Parcelable

class Menu() : Parcelable {
    var Codigo = 0
    var Nombre = ""
    var Precio = 0.0
    var Descripcion = ""

    constructor(Codigo : Int, Nombre:String, Precio:Double, Descripcion : String) : this(){
        this.Codigo = Codigo
        this.Nombre = Nombre
        this.Precio = Precio
        this.Descripcion = Descripcion
    }

    constructor(parcel: Parcel) : this() {
        Codigo = parcel.readInt()
        Nombre = parcel.readString().toString()
        Precio = parcel.readDouble()
        Descripcion = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Codigo)
        parcel.writeString(Nombre)
        parcel.writeDouble(Precio)
        parcel.writeString(Descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Menu> {
        override fun createFromParcel(parcel: Parcel): Menu {
            return Menu(parcel)
        }

        override fun newArray(size: Int): Array<Menu?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Cod: $Codigo | Nombre: $Nombre | Precio: $Precio"
    }

}