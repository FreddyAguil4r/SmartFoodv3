package com.example.smartfood.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartfood.ModelResponse.SupplierResponse
import com.example.smartfood.R

class SupplierAdapter(private val suplierList: List<SupplierResponse>): RecyclerView.Adapter<SupplierAdapter.ViewHolder>() {

    //representa identidad indivual
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var textTitle : TextView
        init {
            textTitle = itemView.findViewById(R.id.item_supplier)
        }
    }

    //su función es inflar la interfaz de usuario de un elemento de la lista crea una instancia de ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_supplier,parent,false)
        return ViewHolder(v)
    }

    //Se encarga de vincular los datos de un conjunto de datos a la interfaz de usuario de un elemento específico en la lista
    override fun onBindViewHolder(holder: SupplierAdapter.ViewHolder, position: Int) {
        val sup = suplierList[position]
        holder.textTitle.text = sup.name

        //holder.textTitle.text = suplierList[position]
    }

    //Informar al RecyclerView sobre la cantidad de elementos que se mostrarán.
    override fun getItemCount(): Int {
        return suplierList.size
        //return titles.size
    }
}