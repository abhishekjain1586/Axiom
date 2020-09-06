package com.axiom.telecom.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.axiom.telecom.db.DBHelper
import com.axiom.telecom.db.entities.MobileHandsetEntity
import com.axiom.telecom.model.FilterTypeBean
import com.axiom.telecom.utils.Constants
import java.util.*
import kotlin.collections.ArrayList

class SearchRepository {

    fun getSearchHandsets(searchedText: String): ArrayList<MobileHandsetEntity> {
        return DBHelper.getInstance().getFavouriteMoviesDao().getSearchedHandsets("%"+searchedText+"%") as ArrayList<MobileHandsetEntity>
    }

    fun getFilteredContentDistinctByType(searchedText: String, typeID: Int): ArrayList<MobileHandsetEntity> {
        when(typeID) {
            FilterTypeBean.TYPE_BRAND -> {
                return DBHelper.getInstance().getFavouriteMoviesDao().getFilteredContentDistinctByBrand("%"+searchedText+"%") as ArrayList<MobileHandsetEntity>
            }

            FilterTypeBean.TYPE_PHONE -> {
                return DBHelper.getInstance().getFavouriteMoviesDao().getFilteredContentDistinctByPhone("%"+searchedText+"%") as ArrayList<MobileHandsetEntity>
            }

            FilterTypeBean.TYPE_PRICE -> {
                return DBHelper.getInstance().getFavouriteMoviesDao().getFilteredContentDistinctByPrice("%"+searchedText+"%") as ArrayList<MobileHandsetEntity>
            }

            FilterTypeBean.TYPE_SIM -> {
                return DBHelper.getInstance().getFavouriteMoviesDao().getFilteredContentDistinctBySim("%"+searchedText+"%") as ArrayList<MobileHandsetEntity>
            }

            FilterTypeBean.TYPE_GPS -> {
                return DBHelper.getInstance().getFavouriteMoviesDao().getFilteredContentDistinctByGps("%"+searchedText+"%") as ArrayList<MobileHandsetEntity>
            }

            FilterTypeBean.TYPE_AUDIO_JACK -> {
                return DBHelper.getInstance().getFavouriteMoviesDao().getFilteredContentDistinctByAudioJack("%"+searchedText+"%") as ArrayList<MobileHandsetEntity>
            }

            else -> {
                return DBHelper.getInstance().getFavouriteMoviesDao().getFilteredContentDistinctByBrand("%"+searchedText+"%") as ArrayList<MobileHandsetEntity>
            }
        }
    }

    fun searchedHandsetsByFilterOptions(searchedText: String, typeLst: ArrayList<FilterTypeBean>) : ArrayList<MobileHandsetEntity>? {
        var strInitialQuery = "select * from " + MobileHandsetEntity.TABLE_NAME + " where "+ MobileHandsetEntity.COLUMN_NAME_PHONE + " LIKE " + "\"%"+searchedText+"%\"" + " AND "
        var strAppendQuery: String = Constants.EMPTY

        for (typeObj in typeLst) {
            if (typeObj.entityList.isNotEmpty()) {
                var columnName: String = Constants.EMPTY
                when(typeObj.type) {
                    FilterTypeBean.TYPE_BRAND -> {
                        columnName = MobileHandsetEntity.COLUMN_NAME_BRAND
                    }

                    FilterTypeBean.TYPE_PHONE -> {
                        columnName = MobileHandsetEntity.COLUMN_NAME_PHONE
                    }

                    FilterTypeBean.TYPE_PRICE -> {
                        columnName = MobileHandsetEntity.COLUMN_NAME_PRICE_EUR
                    }

                    FilterTypeBean.TYPE_SIM -> {
                        columnName = MobileHandsetEntity.COLUMN_NAME_SIM
                    }

                    FilterTypeBean.TYPE_GPS -> {
                        columnName = MobileHandsetEntity.COLUMN_NAME_GPS
                    }

                    FilterTypeBean.TYPE_AUDIO_JACK -> {
                        columnName = MobileHandsetEntity.COLUMN_NAME_AUDIO_JACK
                    }
                }
                var isFilterContentSelected = false
                var commaSeperatedLst = ArrayList<String>()
                for (i in 0 until typeObj.entityList.size) {
                    if (typeObj.entityList.get(i).isSelected) {
                        isFilterContentSelected = true
                        if (typeObj.type == FilterTypeBean.TYPE_PRICE) {
                            commaSeperatedLst.add(typeObj.entityList.get(i).entityName!!)
                        } else {
                            commaSeperatedLst.add("\"" + typeObj.entityList.get(i).entityName!! + "\"")
                        }
                    }
                }
                if (isFilterContentSelected) {
                    strAppendQuery = strAppendQuery + columnName + " IN(" + Arrays.toString(commaSeperatedLst.toArray()).replace("[", "").replace("]","").trim() + ") AND "
                }
            }
        }

        if (strAppendQuery.trim().endsWith("AND")) {
            strAppendQuery = strAppendQuery.substring(0, strAppendQuery.trim().lastIndexOf("AND")).trim()
        }

        return DBHelper.getInstance().getFavouriteMoviesDao().searchedHandsetsByFilterOptions(SimpleSQLiteQuery(strInitialQuery + strAppendQuery)) as ArrayList<MobileHandsetEntity>
    }
}