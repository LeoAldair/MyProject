package com.example.myproject.remote

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class ProductEntry(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("image")
    val urlImage: String,
    @SerializedName("rating")
    val rating: RatingEntry,
)
data class RatingEntry(
    @SerializedName("rate")
    val rate: Float,
    @SerializedName("count")
    val count: Int
)
