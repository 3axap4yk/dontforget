package ru.godsonpeya.dontforget.ui.wordlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.godsonpeya.dontforget.repository.CategoryRepository


class WordViewModelFactory(private val mRepository: CategoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(mRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}