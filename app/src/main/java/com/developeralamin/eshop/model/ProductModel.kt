package com.developeralamin.eshop.model

data class ProductModel(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)