package com.irmablanco.restaurantapplication.fragment

import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

import com.irmablanco.restaurantapplication.R
import com.irmablanco.restaurantapplication.model.Table
import com.irmablanco.restaurantapplication.model.Tables


class TableListFragment : Fragment() {

    companion object {


        fun newInstance(): TableListFragment {
            return TableListFragment()
        }
    }

    lateinit var root: View
    private var onTableSelectedListener: OnTableSelectedListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        if (inflater != null){
            root = inflater.inflate(R.layout.fragment_table_list, container, false)
            val list = root.findViewById<ListView>(R.id.table_list)
            val adapter = ArrayAdapter<Table>(activity, android.R.layout.simple_list_item_1, Tables.toArray())
            list.adapter = adapter

            // Averiguamos que se ha pulsado un elemento de la lista
            list.setOnItemClickListener { parent, view, position, id ->
                // Aviso al listener del fragment
                onTableSelectedListener?.onTableSelected(Tables.get(position), position)
            }
        }

        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonAttach(context)
    }
    //TODO: Handle deprecations
    @Suppress("OverridingDeprecatedMember", "DEPRECATION")
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        // elimino la referencia
        onTableSelectedListener = null
    }

    // Implemento un método común que llaman los onAttach
    fun commonAttach(listener: Any?){
        // Comprobamos si el listener está escuchando al OnTableSelectedListener
        // Nos suscribimos solo si la actividad implementa la interfaz OnTableSelectedListener
        if (listener is OnTableSelectedListener) {
            onTableSelectedListener = listener
        }

    }

    interface OnTableSelectedListener {
        fun onTableSelected (table: Table?, position: Int)
    }

}

