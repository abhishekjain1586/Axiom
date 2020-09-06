package com.axiom.telecom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.axiom.telecom.R
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.viewholder.MobileHandsetViewHolder
import com.axiom.telecom.viewholder.OnItemClickListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class MobileHandsetAdapter(context: Context) : RecyclerView.Adapter<MobileHandsetViewHolder>() {

    private val handsetsLst = ArrayList<MobileHandsetEntity>()
    private val mContext = context
    private var mListener: OnItemClickListener<MobileHandsetEntity>? = null

    fun setData(lst: ArrayList<MobileHandsetEntity>) {
        handsetsLst.clear()
        handsetsLst.addAll(lst)
    }

    fun setListener(listener: OnItemClickListener<MobileHandsetEntity>) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MobileHandsetViewHolder {
        val view : View = LayoutInflater.from(mContext).inflate(R.layout.item_mobile_handset, parent, false)
        return MobileHandsetViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MobileHandsetViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)

        if (payloads.size > 0) {
            val obj = handsetsLst.get(holder.adapterPosition)

            obj.isSelected = !obj.isSelected
            if (obj.isSelected) {
                holder.group1.visibility = View.VISIBLE
                holder.group2.visibility = View.GONE
            } else {
                holder.group1.visibility = View.GONE
                holder.group2.visibility = View.VISIBLE
            }
        }
    }

    override fun onBindViewHolder(holder: MobileHandsetViewHolder, position: Int) {
        val obj = handsetsLst.get(holder.adapterPosition)
        obj.picture?.let {
            Picasso.with(mContext).load(it)
                .resize(200, 200)
                .centerInside()
                .placeholder(mContext.resources.getDrawable(R.drawable.no_picture_256, null))
                .error(ContextCompat.getDrawable(mContext, R.drawable.no_picture_128))
                .into(holder.ivImage, object : Callback {
                    override fun onSuccess() {}
                    override fun onError() {}
                });
        }
        obj.brand?.let {
            holder.tvBrandName1.text = it
            //holder.tvBrandName2.text = it
        }
        obj.phone?.let {
            holder.tvBrandDetail.text = it
            holder.tvBrandDetail2.text = it
        }
        obj.priceEur?.let {
            holder.tvPrice1.text = mContext.resources.getString(R.string.dollar_sign) + it
            holder.tvPrice2.text = mContext.resources.getString(R.string.dollar_sign) + it
        }

        mListener?.let {
            holder.setListener(it, holder.adapterPosition, obj)
        }
    }

    override fun getItemCount(): Int {
        return handsetsLst.size
    }
}