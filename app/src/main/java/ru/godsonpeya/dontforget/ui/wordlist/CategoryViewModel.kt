package ru.godsonpeya.dontforget.ui.wordlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.godsonpeya.dontforget.entity.Category
import ru.godsonpeya.dontforget.entity.CategoryWithParameter
import ru.godsonpeya.dontforget.repository.CategoryRepository


class CategoryViewModel(private val mRepository: CategoryRepository) : ViewModel() {
    val categories: LiveData<List<Category>> = mRepository.allCategory

    val allCategoryWithParams: LiveData<List<CategoryWithParameter>> = mRepository.allCategoryWithParams

    private var _navigateToDetail = MutableLiveData<Boolean?>()
    val navigateToDetail: MutableLiveData<Boolean?>
        get() = _navigateToDetail

    private var _navigateBackToList = MutableLiveData<Boolean?>()
    val navigateBackToList: MutableLiveData<Boolean?>
        get() = _navigateBackToList

    fun insert(category: Category) {
        viewModelScope.launch {
            mRepository.insert(category)
            doNavigation()
        }
    }

    fun doNavigation() {
        _navigateBackToList.postValue(true)
        _navigateToDetail.postValue(true)
    }

    fun navigationDone() {
        _navigateBackToList.postValue(null)
        _navigateToDetail.postValue(null)
    }

    fun deleteWord(myCategory: Category) {
        viewModelScope.launch {
            mRepository.deleteOne(myCategory)
        }
    }

}