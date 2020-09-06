package com.axiom.telecom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.axiom.telecom.R
import com.axiom.telecom.model.FilterTypeBean
import com.axiom.telecom.viewholder.FilterTypeViewHolder
import com.axiom.telecom.viewholder.OnItemClickListener

class FilterTypeAdapter(context: Context) : RecyclerView.Adapter<FilterTypeViewHolder>() {

    private val mContext = context
    private val filterTypeLst = ArrayList<FilterTypeBean>()
    private var mListener: OnItemClickListener<FilterTypeBean>? = null

    fun setData(lst: ArrayList<FilterTypeBean>) {
        filterTypeLst.clear()
        filterTypeLst.addAll(lst)
    }

    fun setListener(listener: OnItemClickListener<FilterTypeBean>) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterTypeViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.item_filter_type, parent, false)
        return FilterTypeViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterTypeViewHolder, position: Int) {
        val obj = filterTypeLst.get(holder.adapterPosition)
        obj.typeName?.let {
            holder.tvTypeName.text = it + " (" + obj.entityList.size + ")"
        }

        mListener?.let {
            holder.setListener(it, holder.adapterPosition, obj)
        }
    }

    override fun getItemCount(): Int {
        return filterTypeLst.size
    }
}