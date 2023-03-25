package com.developeralamin.eshop.api

import com.developeralamin.eshop.model.ProductModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("products")
    suspend fun getProductData():Response<ProductModel>

}