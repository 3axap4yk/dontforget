package ru.godsonpeya.dontforget.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.godsonpeya.dontforget.entity.Category
import ru.godsonpeya.dontforget.entity.Parameter


@Dao
interface ParameterDao {
    @Insert
    fun insert(parameter: Parameter)

    @Insert
    fun insertAll(vararg parameters: Parameter)

    @Query("DELETE FROM parameter")
    fun deleteAll()

    @Delete
    fun deleteWord(parameter: Parameter)

    @get:Query("SELECT * from parameter ORDER BY name ASC")
    val allWords: LiveData<List<Parameter>>

//    @Query("SELECT * from word_table ORDER BY word ASC")
//    fun getAllWords(): LiveData<List<Category?>?>?
}