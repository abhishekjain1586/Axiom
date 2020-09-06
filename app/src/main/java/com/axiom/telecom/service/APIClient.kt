package com.axiom.telecom.service

import com.axiom.telecom.db.entities.MobileHandsetEntity
import retrofit2.Call
import retrofit2.http.GET

interface APIClient {

    // Get all Mobile Handsets
    @GET("b/5f3a3fcf4d939910361666fe/latest")
    fun getAllMobileHandset() : Call<ArrayList<MobileHandsetEntity>>
}