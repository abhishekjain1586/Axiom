package com.axiom.telecom.viewholder

interface OnItemClickListener<T> {

    fun onItemClick(position: Int, obj: T, actionType: Int)
}