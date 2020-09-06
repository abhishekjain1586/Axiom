package com.axiom.telecom.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axiom.telecom.R
import com.axiom.telecom.model.FilterTypeBean

class FilterTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var mListener: OnItemClickListener<FilterTypeBean>? = null
    private var filterTypeObj: FilterTypeBean? = null
    private var mPosition = 0;

    val tvTypeName = itemView.findViewById<TextView>(R.id.tv_type_name)

    fun setListener(listener: OnItemClickListener<FilterTypeBean>, position: Int, obj: FilterTypeBean) {
        mListener = listener
        mPosition = position
        filterTypeObj = obj

        tvTypeName.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.tv_type_name -> {
                mListener?.onItemClick(mPosition, filterTypeObj!!, 0)
            }
        }
    }
}