package com.tttrfge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tttrfge.Model.Apis.RickAndMortyApi
import com.tttrfge.ViewModel.SearchActivityViewModel

class SearchViewModelFactory(private val apiService: RickAndMortyApi): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModelFactory::class.java)){
            return SearchActivityViewModel(apiService) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}