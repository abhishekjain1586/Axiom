package com.axiom.telecom.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.axiom.telecom.db.dao.MobileHandsetDao
import com.axiom.telecom.db.entities.MobileHandsetEntity

@Database(entities = arrayOf(MobileHandsetEntity::class), version = 1, exportSchema = false)
abstract class AxiomDatabase : RoomDatabase() {

    abstract fun getFavouriteMoviesDao() : MobileHandsetDao
}