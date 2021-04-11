package ru.godsonpeya.dontforget.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.godsonpeya.dontforget.entity.Category
import ru.godsonpeya.dontforget.entity.CategoryWithParameter


@Dao
interface CategoryDao {
    @Insert
    fun insert(category: Category)

    @Update
    fun update(category: Category)

    @Query("DELETE FROM category")
    fun deleteAll()

    @Delete
    fun deleteWord(category: Category)

    @get:Query("SELECT * from category ORDER BY next_time DESC")
    val allCategories: LiveData<List<Category>>

    @Transaction
    @Query("SELECT * from category ORDER BY next_time DESC")
    fun allCategoriesWithParamaters(): LiveData<List<CategoryWithParameter>>

//    @Query("SELECT * from word_table ORDER BY word ASC")
//    fun getAllWords(): LiveData<List<Category?>?>?
}