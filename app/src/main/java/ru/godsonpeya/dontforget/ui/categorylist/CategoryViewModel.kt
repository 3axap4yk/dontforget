package ru.godsonpeya.dontforget.ui.categorylist

import android.os.CountDownTimer
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

    private val timer: CountDownTimer

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    val allCategoryWithParams: LiveData<List<CategoryWithParameter>> =
        mRepository.allCategoryWithParams

    private var _navigateToDetail = MutableLiveData<Boolean?>()
    val navigateToDetail: MutableLiveData<Boolean?>
        get() = _navigateToDetail

    private var _navigateToShowDetail = MutableLiveData<Category?>()
    val navigateToShowDetail: MutableLiveData<Category?>
        get() = _navigateToShowDetail

    private var _navigateBackToList = MutableLiveData<Boolean?>()
    val navigateBackToList: MutableLiveData<Boolean?>
        get() = _navigateBackToList


    init {
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }
            override fun onFinish() {
                _currentTime.value = DONE
            }
        }
    }
    fun insert(category: Category) {
        viewModelScope.launch {
            if (category.id != null) {
                mRepository.update(category)
            } else {
                mRepository.insert(category)
            }
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
        _navigateToShowDetail.postValue(null)
    }

    fun deleteWord(myCategory: Category) {
        viewModelScope.launch {
            mRepository.deleteOne(myCategory)
        }
    }

    fun onClickCategory(category: Category) {
        _navigateToShowDetail.postValue(category)
    }


    companion object {
        // These represent different important times in the game, such as game length.

        // This is when the game is over
        private const val DONE = 0L

        // This is the time when the phone will start buzzing each second
        private const val COUNTDOWN_PANIC_SECONDS = 10L

        // This is the number of milliseconds in a second
        private const val ONE_SECOND = 1000L

        // This is the total time of the game
        private const val COUNTDOWN_TIME = 60000L

    }
}