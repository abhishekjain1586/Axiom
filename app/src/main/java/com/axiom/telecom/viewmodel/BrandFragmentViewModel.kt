package com.axiom.telecom.viewmodel

import androidx.lifecycle.ViewModel
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.repository.MobileHandsetsRepository

class BrandFragmentViewModel : ViewModel() {

    private val lvDataMobileLst = SingleLiveEvent<ArrayList<MobileHandsetEntity>>()
    private val mobileRepository = MobileHandsetsRepository()

    fun getMobileLstByBrand(brandName: String): SingleLiveEvent<ArrayList<MobileHandsetEntity>> {
        lvDataMobileLst.value = mobileRepository.getMobileHandsetByBrand(brandName)
        return lvDataMobileLst
    }
}