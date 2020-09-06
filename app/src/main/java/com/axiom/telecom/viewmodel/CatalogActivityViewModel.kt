package com.axiom.telecom.viewmodel

import androidx.lifecycle.ViewModel
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.repository.MobileHandsetsRepository

class CatalogActivityViewModel: ViewModel(), MobileHandsetsRepository.OnMobileHandsetListener {

    private val loader = SingleLiveEvent<Boolean>()
    private val errorMsg = SingleLiveEvent<String>()
    private val lvDataMobileLst = SingleLiveEvent<ArrayList<MobileHandsetEntity>>()

    private val mobileRepository = MobileHandsetsRepository()

    fun showLoader() : SingleLiveEvent<Boolean> {
        return loader
    }

    fun showError() : SingleLiveEvent<String> {
        return errorMsg
    }

    fun getMobileBrandsLst(): SingleLiveEvent<ArrayList<MobileHandsetEntity>> {
        fetchMobileBrands()
        return lvDataMobileLst
    }

    private fun fetchMobileBrands() {
        loader.value = true
        mobileRepository.setListener(this)
        mobileRepository.getAllMobileHandsets()
    }

    override fun onMobileHandsetSuccess(lst: ArrayList<MobileHandsetEntity>) {
        loader.value = false
        lvDataMobileLst.value = lst
    }

    override fun onMobileHandsetFailure(errorMsg: String) {
        loader.value = false
        this.errorMsg.value= errorMsg
    }
}