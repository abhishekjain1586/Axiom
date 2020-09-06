package com.axiom.telecom.service

import com.axiom.telecom.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceHelper {

    private const val BASE_URL = "https://api.jsonbin.io/"
    //const val ROUTE = "b/5f3a3fcf4d939910361666fe/latest"
    const val SECRET_KEY = "\$2b\$10\$ldwbG.B/2hNRvS2dzXDxoO5P87sYGwoE02SliZIh.8vlvsSctGqF2"

    private val logging = HttpLoggingInterceptor()

    fun getClient(): APIClient {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                val request = originalRequest.newBuilder()
                    .header(Constants.HEADER_SECRET_KEY, SECRET_KEY)
                    .method(originalRequest.method(), originalRequest.body())
                    .build()
                return chain.proceed(request)
            }
        }

        val okHttpClient : OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(httpInterceptor)
            .addInterceptor(logging)
            .build()

        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(APIClient::class.java)
    }
}