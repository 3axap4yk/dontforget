package ru.godsonpeya.dontforget.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.godsonpeya.dontforget.entity.Category
import ru.godsonpeya.dontforget.entity.CategoryWithParameter
import ru.godsonpeya.dontforget.local.dao.CategoryDao


class CategoryRepository(private val categoryDao: CategoryDao) {
    val allCategory: LiveData<List<Category>> = categoryDao.allCategories
    val allCategoryWithParams: LiveData<List<CategoryWithParameter>> = categoryDao.allCategoriesWithParamaters()

    suspend fun insert(category: Category) {
        withContext(Dispatchers.IO) {
            categoryDao.insert(category)
        }
    }

    suspend fun deleteOne(category: Category) {
        withContext(Dispatchers.IO) {
            categoryDao.deleteWord(category)
        }
    }
}