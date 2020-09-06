package com.axiom.telecom.db

import androidx.room.Room
import com.axiom.telecom.application.MyApp

object DBHelper {

    private var dbInstance_ : AxiomDatabase? = null

    fun getInstance() : AxiomDatabase {
        if (dbInstance_ == null) {
            dbInstance_ = Room.databaseBuilder(
                MyApp._INSTANCE.applicationContext,
                AxiomDatabase::class.java,
                "axiom_db.db")
                .allowMainThreadQueries()
                .build()
        }
        return dbInstance_ as AxiomDatabase
    }
}