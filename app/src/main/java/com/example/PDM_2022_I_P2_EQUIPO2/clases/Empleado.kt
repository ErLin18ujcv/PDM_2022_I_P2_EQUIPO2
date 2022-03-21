package com.example.PDM_2022_I_P2_EQUIPO2.clases

import android.os.Parcel
import android.os.Parcelable

class Empleado() :Parcelable {
    var Codigo = 0
    var Nombre = ""
    var Puesto = ""

    constructor(Codigo : Int, Nombre: String, Puesto : String):this(){
        this.Codigo = Codigo
        this.Nombre = Nombre
        this.Puesto = Puesto
    }

    constructor(parcel: Parcel) : this() {
        Codigo = parcel.readInt()
        Nombre = parcel.readString().toString()
        Puesto = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Codigo)
        parcel.writeString(Nombre)
        parcel.writeString(Puesto)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Empleado> {
        override fun createFromParcel(parcel: Parcel): Empleado {
            return Empleado(parcel)
        }

        override fun newArray(size: Int): Array<Empleado?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Código: $Codigo | Nombre: $Nombre | Puesto: $Puesto"
    }

}