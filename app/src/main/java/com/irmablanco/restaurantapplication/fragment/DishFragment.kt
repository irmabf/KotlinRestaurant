package com.irmablanco.restaurantapplication.fragment


import android.app.Fragment
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.irmablanco.restaurantapplication.R
import com.irmablanco.restaurantapplication.model.Dish
import com.irmablanco.restaurantapplication.model.Table
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import android.widget.ViewSwitcher


class DishFragment : Fragment() {

    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        DISH(1)
    }
    companion object {

        val ARG_TABLE = "ARG_TABLE"

        // Creo esta funcion para recibir la instancia de este Fragment, con la mesa como argumento
        fun newInstance(table: Table): DishFragment {
            val fragment = DishFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARG_TABLE, table)
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var root: View
    lateinit var viewSwitcher: ViewSwitcher

    var table: Table? = null
        set(value){
            field = value
            if(value != null){
                dish = value.dish
            }
        }

    var dish: Dish? = null
        set(value){
            field = value
            // Accedemos a las vistas
            val dishImage = root.findViewById<ImageView>(R.id.dish_image)
            val dishName = root.findViewById<TextView>(R.id.dish_name)
            val dishDescription = root.findViewById<TextView>(R.id.dish_description)
            val dishPrice = root.findViewById<TextView>(R.id.dish_price)

            // Actualizo la vista con el modelo
            if (value != null) {
                dishImage.setImageResource(value.image)
                dishName.text = value.name
                dishDescription.text = value.description

                val priceString = getString(R.string.dish_price, value.price)
                dishPrice.text = priceString

                viewSwitcher.displayedChild = VIEW_INDEX.DISH.index
            }
            else {
                updateDish()
            }
        }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        // Si inflater es distinto de null
        if (inflater != null) {

            root = inflater.inflate(R.layout.fragment_dish, container, false)
            viewSwitcher = root.findViewById(R.id.view_switcher)
            viewSwitcher.setInAnimation(activity, android.R.anim.fade_in)
            viewSwitcher.setOutAnimation(activity, android.R.anim.fade_out)

            if (arguments != null){
                table = arguments.getSerializable(ARG_TABLE) as? Table
            }
        }

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    private fun updateDish() {
        viewSwitcher.displayedChild = VIEW_INDEX.LOADING.index

        async(UI) {
            // Esto ejecuta la descarga en 2º plano
            val newDish: Deferred<Dish?> = bg {
                downloadDish(table)
            }

            dish = newDish.await()

        }

    }

    fun downloadDish(table: Table?): Dish? {
        try {
            // Descargo los datos de mocky api
            var url = URL("http://www.mocky.io/v2/5b142a183100005a0078bf3c")
            val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()

            val jsonRoot = JSONObject(jsonString)
            val listDish = jsonRoot.getJSONArray("platos")
            val plato = listDish.getJSONObject(0)
            val name = plato.getString("nombre")
            val description = plato.getString("descripcion")
            val alergen = plato.getString("alergenos")
            val price = plato.getDouble("precio").toFloat()
            val image = plato.getString("image")

            // Convertimos el texto imageString a Drawable
            val imageInt = image.toInt()
            val imageResource = when (imageInt){
                1 -> R.drawable.pancakes_postre
                2 -> R.drawable.plato_donut
                else -> R.drawable.pancakes_postre
            }

            Thread.sleep(5000)

            return Dish(name, imageResource, price, description, alergen)

        } catch (ex: Exception){
            ex.printStackTrace()
        }

        return null
    }

}