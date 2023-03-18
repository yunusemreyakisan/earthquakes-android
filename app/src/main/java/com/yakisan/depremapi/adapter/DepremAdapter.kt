package com.yakisan.depremapi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yakisan.depremapi.R
import com.yakisan.depremapi.databinding.EarthquakeItemBinding
import com.yakisan.depremapi.model.DepremModel

class DepremAdapter(val depremler: ArrayList<DepremModel>) :
    RecyclerView.Adapter<DepremAdapter.VH>(), ClickListener {

    //ViewHolder
    class VH(val view: EarthquakeItemBinding) : RecyclerView.ViewHolder(view.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = DataBindingUtil.inflate<EarthquakeItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.earthquake_item, parent, false
        )
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.view.item = depremler[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return depremler.size
    }

    //update list
    fun updateEarthquakes(liste: ArrayList<DepremModel>) {
        depremler.clear()
        depremler.addAll(liste)
        notifyDataSetChanged()
    }

    override fun clicked(view: View) {

    }


}