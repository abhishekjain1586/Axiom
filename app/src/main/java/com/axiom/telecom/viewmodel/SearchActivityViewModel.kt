package com.axiom.telecom.viewmodel

import androidx.lifecycle.ViewModel
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.model.FilterTypeBean
import com.axiom.telecom.model.FilterTypeContentBean
import com.axiom.telecom.repository.SearchRepository
import com.axiom.telecom.utils.Constants

class SearchActivityViewModel : ViewModel() {

    private val errorMsg = SingleLiveEvent<String>()
    private val lvEnableFilter = SingleLiveEvent<Boolean>()
    private val lvEnableResetFilter = SingleLiveEvent<Boolean>()
    private val lvDataMobileLst = SingleLiveEvent<ArrayList<MobileHandsetEntity>>()
    private val lvDataFilterType = SingleLiveEvent<ArrayList<FilterTypeBean>>()
    private val lvResetFilter = SingleLiveEvent<Boolean>()

    private val searchRepository = SearchRepository()

    private val filterTypeLst = ArrayList<FilterTypeBean>()

    fun showError() : SingleLiveEvent<String> {
        return errorMsg
    }

    fun hideFilterOption() {
        lvEnableFilter.value = false
    }

    fun hideOrShowResetFilterOption(value: Boolean) {
        lvEnableResetFilter.value = value
    }

    fun showFilterOption() : SingleLiveEvent<Boolean> {
        return lvEnableFilter
    }

    fun getResetFilterOption() : SingleLiveEvent<Boolean> {
        return lvEnableResetFilter
    }

    fun getSearchedHandsets(searchedText: String): SingleLiveEvent<ArrayList<MobileHandsetEntity>> {
        if (checkFilterOptionAvailable()) {
            searchByFilterOptions(searchedText)
        } else {
            searchByUserSearchedText(searchedText)
        }
        hideOrShowResetFilterOption(false)
        return lvDataMobileLst
    }

    private fun checkFilterOptionAvailable(): Boolean {
        for (filterTypeObj in filterTypeLst) {
            for (filterContentObj in filterTypeObj.entityList) {
                if (filterContentObj.isSelected) {
                    return true
                }
            }
        }
        return false
    }

    private fun searchByFilterOptions(searchedText: String) {
        val lst = searchRepository.searchedHandsetsByFilterOptions(searchedText, filterTypeLst)
        if (lst.isNullOrEmpty()) {
            errorMsg.value = Constants.EMPTY
        } else {
            lvDataMobileLst.value = lst
        }
        lvEnableFilter.value = true
    }

    private fun searchByUserSearchedText(searchedText: String) {
        val lst = searchRepository.getSearchHandsets(searchedText)
        if (lst.isNullOrEmpty()) {
            errorMsg.value = Constants.EMPTY
            lvEnableFilter.value = false
        } else {
            lvDataMobileLst.value = lst
            lvEnableFilter.value = true
        }
    }

    fun getFilterDataForSearchedText(searchedText: String): SingleLiveEvent<ArrayList<FilterTypeBean>> {
        if (filterTypeLst.isEmpty()) {
            val tempLst = getFilterTypeLst()
            setContentForFilterType(searchedText, tempLst)
            filterTypeLst.addAll(tempLst)
        }
        lvDataFilterType.value = filterTypeLst
        hideFilterOption()
        hideOrShowResetFilterOption(true)
        return lvDataFilterType
    }

    fun resetFilterData() {
        filterTypeLst.clear()
        lvResetFilter.value = true
    }

    fun resetFilterDataObserver(): SingleLiveEvent<Boolean> {
        return lvResetFilter
    }

    private fun setContentForFilterType(searchedText:String, typeLst: ArrayList<FilterTypeBean>) {
        for (typeObj in typeLst) {
            val distinctHandsetLst = searchRepository.getFilteredContentDistinctByType(searchedText, typeObj.type!!)
            val contentLst = typeObj.entityList
            contentLst.clear()
            for (handsetObj in distinctHandsetLst) {
                val newContentObj = FilterTypeContentBean()
                when(typeObj.type) {
                    FilterTypeBean.TYPE_BRAND -> {
                        newContentObj.entityName = handsetObj.brand
                    }

                    FilterTypeBean.TYPE_PHONE -> {
                        newContentObj.entityName = handsetObj.phone
                    }

                    FilterTypeBean.TYPE_PRICE -> {
                        newContentObj.entityName = Constants.EMPTY + handsetObj.priceEur
                    }

                    FilterTypeBean.TYPE_SIM -> {
                        newContentObj.entityName = handsetObj.sim
                    }

                    FilterTypeBean.TYPE_GPS -> {
                        newContentObj.entityName = handsetObj.gps
                    }

                    FilterTypeBean.TYPE_AUDIO_JACK -> {
                        newContentObj.entityName = handsetObj.audioJack
                    }
                }
                contentLst.add(newContentObj)
            }

        }
    }

    private fun getFilterTypeLst(): ArrayList<FilterTypeBean> {
        val filterLst = ArrayList<FilterTypeBean>()

        val objBrand = FilterTypeBean()
        objBrand.type = FilterTypeBean.TYPE_BRAND
        objBrand.typeName = FilterTypeBean.TYPE_NAME_BRAND
        filterLst.add(objBrand)

        val objPhone = FilterTypeBean()
        objPhone.type = FilterTypeBean.TYPE_PHONE
        objPhone.typeName = FilterTypeBean.TYPE_NAME_PHONE
        filterLst.add(objPhone)

        val objPrice = FilterTypeBean()
        objPrice.type = FilterTypeBean.TYPE_PRICE
        objPrice.typeName = FilterTypeBean.TYPE_NAME_PRICE
        filterLst.add(objPrice)

        val objSim = FilterTypeBean()
        objSim.type = FilterTypeBean.TYPE_SIM
        objSim.typeName = FilterTypeBean.TYPE_NAME_SIM
        filterLst.add(objSim)

        val objGps = FilterTypeBean()
        objGps.type = FilterTypeBean.TYPE_GPS
        objGps.typeName = FilterTypeBean.TYPE_NAME_GPS
        filterLst.add(objGps)

        val objAudioJack = FilterTypeBean()
        objAudioJack.type = FilterTypeBean.TYPE_AUDIO_JACK
        objAudioJack.typeName = FilterTypeBean.TYPE_NAME_AUDIO_JACK
        filterLst.add(objAudioJack)

        return filterLst
    }
}