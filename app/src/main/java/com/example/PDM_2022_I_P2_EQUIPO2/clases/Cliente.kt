package com.example.PDM_2022_I_P2_EQUIPO2.clases

import android.os.Parcel
import android.os.Parcelable

class Cliente() : Parcelable {
    var Id = 0
    var Nombre = ""
    var Correo = ""

    constructor(Id : Int, Nombre : String, Correo : String) : this() {
        this.Id = Id
        this.Nombre = Nombre
        this.Correo = Correo
    }

    constructor(parcel: Parcel) : this() {
        Id = parcel.readInt()
        Nombre = parcel.readString().toString()
        Correo = parcel.readString().toString()
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Id)
        parcel.writeString(Nombre)
        parcel.writeString(Correo)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cliente> {
        override fun createFromParcel(parcel: Parcel): Cliente {
            return Cliente(parcel)
        }

        override fun newArray(size: Int): Array<Cliente?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "ID: $Id | Nombre: $Nombre | Correo: $Correo"
    }

}