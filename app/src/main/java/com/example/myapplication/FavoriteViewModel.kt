package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Item

class FavoriteViewModel : ViewModel() {
    private val _favoriteItems = MutableLiveData<List<Item>>()
    val favoriteItems: LiveData<List<Item>> = _favoriteItems

    fun addToFavorites(item: Item) {
        val currentList = _favoriteItems.value.orEmpty().toMutableList()
        currentList.add(item)
        _favoriteItems.value = currentList
    }
}