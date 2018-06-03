package com.irmablanco.restaurantapplication.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import com.irmablanco.restaurantapplication.fragment.TablePagerFragment
import com.irmablanco.restaurantapplication.R
import com.irmablanco.restaurantapplication.fragment.TableListFragment
import com.irmablanco.restaurantapplication.model.Dish
import com.irmablanco.restaurantapplication.model.Table
import com.irmablanco.restaurantapplication.model.Tables

class DishActivity : AppCompatActivity(), TableListFragment.OnTableSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        // Comprobamos si en la interfaz tenemos un FrameLayout llamado table_list_fragment
        if (findViewById<View>(R.id.table_list_fragment) != null) {
            // Comprobamos primero que no esta añadido el fragment para que no se nos vayan acumulando cada vez que giremos la pantalla
            if (fragmentManager.findFragmentById(R.id.table_list_fragment) == null) {
                val fragment = TableListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.table_list_fragment, fragment)
                        .commit()
        }
    }
        // Comprobamos si en la interfaz tenemos un FrameLayout llamado table_pager_fragment
        if (findViewById<View>(R.id.fragment_table_pager) != null) {
            // Comprobamos primero que no esta añadido el fragment, porque sino se añade cada vez que recreamos la actividad
            if (fragmentManager.findFragmentById(R.id.fragment_table_pager) == null) {
                val fragment = TablePagerFragment.newInstance(0)
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_table_pager, fragment)
                        .commit()
            }
        }
    }
    override fun onTableSelected(table: Table?, position: Int) {
        startActivity(TablePagerActivity.intent(this, position))
    }

}


