package com.example.smartfood.ModelResponse

data class CategoryResponse (
    var id : Int,
    var name : String,
    var totalValuesCategories : Double,
    var inventory : InventoryResponse
)