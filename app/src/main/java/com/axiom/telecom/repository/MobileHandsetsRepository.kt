package com.axiom.telecom.repository

import com.axiom.telecom.R
import com.axiom.telecom.application.MyApp
import com.axiom.telecom.db.DBHelper
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.service.ServiceHelper
import com.axiom.telecom.utils.NetworkUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileHandsetsRepository {

    private var mListener: OnMobileHandsetListener? = null

    interface OnMobileHandsetListener {
        fun onMobileHandsetSuccess(lst : ArrayList<MobileHandsetEntity>)
        fun onMobileHandsetFailure(errorMsg : String)
    }

    fun setListener(listener: OnMobileHandsetListener) {
        mListener = listener
    }

    fun getAllMobileHandsets() {

        if (NetworkUtil.isNetworkConnected()) {
            val callback = ServiceHelper.getClient().getAllMobileHandset()
            callback.enqueue(object : Callback<ArrayList<MobileHandsetEntity>> {
                override fun onResponse(
                    call: Call<ArrayList<MobileHandsetEntity>>,
                    response: Response<ArrayList<MobileHandsetEntity>>
                ) {
                    if (response.isSuccessful) {
                        val tempLst = response.body() as ArrayList<MobileHandsetEntity>
                        if (!tempLst.isNullOrEmpty()) {
                            DBHelper.getInstance().getFavouriteMoviesDao().insert(tempLst)
                            mListener?.onMobileHandsetSuccess(getAllBrands())
                        } else {
                            val brandsLst = getAllBrands()
                            if (!brandsLst.isNullOrEmpty()) {
                                mListener?.onMobileHandsetSuccess(brandsLst)
                            } else {
                                mListener?.onMobileHandsetFailure(MyApp._INSTANCE.resources.getString(R.string.text_no_record_found))
                            }
                        }
                    } else {
                        val brandsLst = getAllBrands()
                        if (!brandsLst.isNullOrEmpty()) {
                            mListener?.onMobileHandsetSuccess(brandsLst)
                        } else {
                            mListener?.onMobileHandsetFailure(MyApp._INSTANCE.resources.getString(R.string.error_server))
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<MobileHandsetEntity>>, t: Throwable) {
                    val brandsLst = getAllBrands()
                    if (!brandsLst.isNullOrEmpty()) {
                        mListener?.onMobileHandsetSuccess(brandsLst)
                    } else {
                        mListener?.onMobileHandsetFailure(MyApp._INSTANCE.resources.getString(R.string.error_oops_something_not_right))
                    }
                }
            })
        } else {
            val brandsLst = getAllBrands()
            if (!brandsLst.isNullOrEmpty()) {
                mListener?.onMobileHandsetSuccess(brandsLst)
            } else {
                mListener?.onMobileHandsetFailure(MyApp._INSTANCE.resources.getString(R.string.error_network_connection))
            }
        }
    }

    private fun getAllBrands(): ArrayList<MobileHandsetEntity> {
        return DBHelper.getInstance().getFavouriteMoviesDao().getAllBrands() as ArrayList<MobileHandsetEntity>
    }

    fun getMobileHandsetByBrand(brandname: String): ArrayList<MobileHandsetEntity> {
        return DBHelper.getInstance().getFavouriteMoviesDao().getAllMobilesByBrand(brandname) as ArrayList<MobileHandsetEntity>
    }
}