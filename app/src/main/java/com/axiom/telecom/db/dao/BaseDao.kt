package com.axiom.telecom.db.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(lst: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(type: T): Long

    @Update
    abstract fun update(type: T)

    @Update
    abstract fun update(type: List<T>)

    @Transaction
    open fun insertOrUpdate(type: T) {
        val rowId: Long = insert(type)
        if (rowId == -1L) {
            update(type)
        }
    }

    @Transaction
    open fun insertOrUpdate(objList: List<T>) {
        val insertResult = insert(objList)
        val updateList = mutableListOf<T>()

        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) updateList.add(objList[i])
        }

        if (!updateList.isEmpty()) update(updateList)
    }

    @Delete
    abstract fun delete(type: T)

    @RawQuery
    abstract fun searchedHandsetsByFilterOptions(query: SupportSQLiteQuery) : List<T>?
}