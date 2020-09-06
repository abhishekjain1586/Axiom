package com.axiom.telecom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.axiom.telecom.R
import com.axiom.telecom.model.FilterTypeContentBean
import com.axiom.telecom.viewholder.FilterContentViewHolder
import com.axiom.telecom.viewholder.OnItemClickListener

class FilterContentAdapter(context: Context) : RecyclerView.Adapter<FilterContentViewHolder>() {

    private val mContext = context
    private var mListener: OnItemClickListener<FilterTypeContentBean>? = null
    private val contentLst = ArrayList<FilterTypeContentBean>()

    fun setData(lst: ArrayList<FilterTypeContentBean>) {
        contentLst.clear()
        contentLst.addAll(lst)
    }

    fun setListener(listener: OnItemClickListener<FilterTypeContentBean>) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterContentViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.item_filter_content, parent, false)
        return FilterContentViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: FilterContentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.size > 0) {
            contentLst.get(holder.adapterPosition).isSelected = (payloads.get(0) as FilterTypeContentBean).isSelected
            if (contentLst.get(holder.adapterPosition).isSelected) {
                holder.tvContentName.background = mContext.getDrawable(R.drawable.bg_filter_selected)
            } else {
                holder.tvContentName.background = mContext.getDrawable(R.drawable.bg_filter_unselected)
            }
        }
    }

    override fun onBindViewHolder(holder: FilterContentViewHolder, position: Int) {
        val obj = contentLst.get(holder.adapterPosition)
        obj.entityName?.let {
            holder.tvContentName.text = it
        }

        if (obj.isSelected) {
            holder.tvContentName.background = mContext.getDrawable(R.drawable.bg_filter_selected)
        } else {
            holder.tvContentName.background = mContext.getDrawable(R.drawable.bg_filter_unselected)
        }

        mListener?.let {
            holder.setListener(it, holder.adapterPosition, obj)
        }
    }

    override fun getItemCount(): Int {
        return contentLst.size
    }


}