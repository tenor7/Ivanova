package com.example.myapplication.api

import com.example.myapplication.data.Item
import com.example.myapplication.data.films
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val films: List<Item>
)