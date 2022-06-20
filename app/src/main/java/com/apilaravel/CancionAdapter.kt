package com.apilaravel

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CancionAdapter(val canciones:List<CancionData>): RecyclerView.Adapter<CancionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CancionViewHolder(layoutInflater.inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: CancionViewHolder, position: Int) {
        val item = canciones[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int  = canciones.size

}