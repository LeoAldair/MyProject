package com.example.myproject.remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitBuilder {
    @GET("products/{id}")
    fun getProductById(@Path("id") id: String): Call<ProductEntry>


    companion object{
        private val BASE_URL = "https://fakestoreapi.com/"

        fun create() : RetrofitBuilder{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetrofitBuilder::class.java)
        }
    }
}