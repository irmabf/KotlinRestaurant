package com.irmablanco.restaurantapplication.model

import com.irmablanco.restaurantapplication.R
import java.io.Serializable
//Singleton
object Tables : Serializable {
    private var tables: List<Table> = listOf(
            Table("Mesa 1", Dish("Pancakes", R.drawable.pancakes_postre ,15.95f,"Pancakes al estilo frances","Ninguno")),
            Table("Mesa 2", Dish("Donut azucarado", R.drawable.plato_donut ,15.95f,"Donut cubierto de virutas y azucar","Ninguno"))
//            Table("Mesa 4"),
//            Table("Mesa 5"),
//            Table("Mesa 6"),
//            Table("Mesa 7"),
//            Table("Mesa 8"),
//            Table("Mesa 9"),
//            Table("Mesa 10")
    )

    val count
        get() = tables.size

    operator fun get(index: Int) = tables[index]

    fun toArray() = tables.toTypedArray()

}