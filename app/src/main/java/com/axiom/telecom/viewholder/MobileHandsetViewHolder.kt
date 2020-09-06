package com.axiom.telecom.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.axiom.telecom.R
import com.axiom.telecom.db.entities.MobileHandsetEntity

class MobileHandsetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var mListener: OnItemClickListener<MobileHandsetEntity>? = null
    private var handsetObj: MobileHandsetEntity? = null
    private var mPosition = 0;

    val layMain = itemView.findViewById<ConstraintLayout>(R.id.lay_main)
    val ivImage = itemView.findViewById<ImageView>(R.id.iv_image)
    val group1 = itemView.findViewById<Group>(R.id.group1)
    val tvBrandName1 = itemView.findViewById<TextView>(R.id.tv_brandname1)
    val tvBrandDetail = itemView.findViewById<TextView>(R.id.tv_brand_detail)
    val tvPrice1 = itemView.findViewById<TextView>(R.id.tv_price1)

    val group2 = itemView.findViewById<Group>(R.id.group2)
    val tvBrandDetail2 = itemView.findViewById<TextView>(R.id.tv_brand_detail2)
    val tvPrice2 = itemView.findViewById<TextView>(R.id.tv_price2)

    fun setListener(listener: OnItemClickListener<MobileHandsetEntity>, position: Int, obj: MobileHandsetEntity) {
        mListener = listener
        mPosition = position
        handsetObj = obj

        layMain.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.lay_main -> {
                mListener?.onItemClick(mPosition, handsetObj!!, 0)
            }
        }
    }


}