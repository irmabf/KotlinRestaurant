package com.irmablanco.restaurantapplication.activity

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.irmablanco.restaurantapplication.R
import com.irmablanco.restaurantapplication.fragment.DishFragment
import com.irmablanco.restaurantapplication.model.Tables

class TablePagerActivity : AppCompatActivity() {

    val pager by lazy {findViewById<ViewPager>(R.id.view_pager)}
    val tables = Tables()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_pager)

        // Configuramos la Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setLogo(R.mipmap.ic_launcher)
        setSupportActionBar(toolbar)

        val adapter = object: FragmentPagerAdapter(fragmentManager) {
            // Obtengo la instancia de DishFragment con la función creada
            // y la posición del item con el operador sobrecargado
            override fun getItem(position: Int) =  DishFragment.newInstance(tables[position])

            override fun getCount() = tables.count

            override fun getPageTitle(position: Int) = tables[position].name

        }

        pager.adapter = adapter

        // Método para cambiar el título al cambiar de ViewPager
        pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                updateTableInfo(position)
            }

        })

        updateTableInfo(0)
    }

    // función para refrescar el título segun la posicion
    private fun updateTableInfo(position: Int) {
        supportActionBar?.title = tables[position].name
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.pager, menu)

        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.previous -> {
                pager.currentItem = pager.currentItem - 1
                true
            }
            R.id.next -> {
                pager.currentItem++
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        invalidateOptionsMenu()

        val menuPrev = menu?.findItem(R.id.previous)
        menuPrev?.setEnabled(pager.currentItem > 0)

        val menuNext = menu?.findItem(R.id.next)
        menuNext?.setEnabled(pager.currentItem < tables.count-1)

        return true

    }


}
