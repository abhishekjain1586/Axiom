package com.axiom.telecom.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.axiom.telecom.R
import com.axiom.telecom.model.FilterTypeContentBean

class FilterContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var mListener: OnItemClickListener<FilterTypeContentBean>? = null
    private var filterContentObj: FilterTypeContentBean? = null
    private var mPosition = 0;

    val tvContentName = itemView.findViewById<TextView>(R.id.tv_type_name)

    fun setListener(listener: OnItemClickListener<FilterTypeContentBean>, position: Int, obj: FilterTypeContentBean) {
        mListener = listener
        mPosition = position
        filterContentObj = obj

        tvContentName.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.tv_type_name -> {
                mListener?.onItemClick(mPosition, filterContentObj!!, 0)
            }
        }
    }
}