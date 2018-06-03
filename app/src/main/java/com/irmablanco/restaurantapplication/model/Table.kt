package com.irmablanco.restaurantapplication.model

import java.io.Serializable

data class Table (val name: String, val dish: List<Dish>?) : Serializable {

    constructor(name: String) : this(name, null)

    override fun toString(): String {
        return name
    }
}