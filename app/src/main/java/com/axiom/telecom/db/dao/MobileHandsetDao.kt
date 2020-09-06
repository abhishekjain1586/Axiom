package com.axiom.telecom.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.axiom.telecom.db.DBHelper
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.model.FilterTypeBean
import com.axiom.telecom.utils.Constants

@Dao
abstract class MobileHandsetDao : BaseDao<MobileHandsetEntity>() {

    @Query("select * from mobiles where brand=:brandName")
    abstract fun getAllMobilesByBrand(brandName: String) : List<MobileHandsetEntity>?

    @Query("select * from mobiles group by brand")
    abstract fun getAllBrands() : List<MobileHandsetEntity>?

    @Query("select * from mobiles where phone like :searchText")
    abstract fun getSearchedHandsets(searchText: String) : List<MobileHandsetEntity>?

    @Query("select * from mobiles where phone like :searchText group by brand")
    abstract fun getFilteredContentDistinctByBrand(searchText: String) : List<MobileHandsetEntity>?

    @Query("select * from mobiles where phone like :searchText group by phone")
    abstract fun getFilteredContentDistinctByPhone(searchText: String) : List<MobileHandsetEntity>?

    @Query("select * from mobiles where phone like :searchText group by priceEur")
    abstract fun getFilteredContentDistinctByPrice(searchText: String) : List<MobileHandsetEntity>?

    @Query("select * from mobiles where phone like :searchText group by sim")
    abstract fun getFilteredContentDistinctBySim(searchText: String) : List<MobileHandsetEntity>?

    @Query("select * from mobiles where phone like :searchText group by gps")
    abstract fun getFilteredContentDistinctByGps(searchText: String) : List<MobileHandsetEntity>?

    @Query("select * from mobiles where phone like :searchText group by audioJack")
    abstract fun getFilteredContentDistinctByAudioJack(searchText: String) : List<MobileHandsetEntity>?

    @Query("delete from mobiles")
    abstract fun deleteAll()




    /*@Transaction
    fun searchedHandsetsByFilterOptions(searchText: String, typeLst: ArrayList<FilterTypeBean>) : List<MobileHandsetEntity>? {
        var strQuery: String = Constants.EMPTY

        for (typeObj in typeLst) {
            if (typeObj.entityList.isNotEmpty()) {
                var columnName: String = Constants.EMPTY
                when(typeObj.type) {
                    FilterTypeBean.TYPE_BRAND -> {
                        columnName = MobileHandsetEntity.COLUMN_NAME_BRAND
                    }

                    FilterTypeBean.TYPE_PHONE -> {
                        var columnName = MobileHandsetEntity.COLUMN_NAME_PHONE
                    }

                    FilterTypeBean.TYPE_PRICE -> {
                        var columnName = MobileHandsetEntity.COLUMN_NAME_PRICE_EUR
                    }

                    FilterTypeBean.TYPE_SIM -> {
                        var columnName = MobileHandsetEntity.COLUMN_NAME_SIM
                    }

                    FilterTypeBean.TYPE_GPS -> {
                        var columnName = MobileHandsetEntity.COLUMN_NAME_GPS
                    }

                    FilterTypeBean.TYPE_AUDIO_JACK -> {
                        var columnName = MobileHandsetEntity.COLUMN_NAME_AUDIO_JACK
                    }
                }
                for (contentObj in typeObj.entityList) {
                    strQuery = strQuery + columnName + "=" + contentObj.entityName + " OR "
                }
                if (strQuery.trim().endsWith("AND")) {
                    strQuery = strQuery.substring(0, strQuery.trim().lastIndexOf("AND")).trim()
                }
            }
        }

        return searchedHandsetsByFilterOptions(SimpleSQLiteQuery(strQuery))
    }*/

}