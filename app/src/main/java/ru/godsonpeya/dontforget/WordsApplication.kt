package ru.godsonpeya.dontforget

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.godsonpeya.dontforget.local.database.AppRoomDatabase
import ru.godsonpeya.dontforget.repository.CategoryRepository

class WordsApplication : Application() {
    val database by lazy { AppRoomDatabase.getDatabase(this) }
    val repository by lazy { CategoryRepository(database.categoryDao()) }
}