package ru.godsonpeya.dontforget

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.godsonpeya.dontforget.local.database.AppRoomDatabase
import ru.godsonpeya.dontforget.repository.CategoryRepository

class WordsApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { CategoryRepository(database.categoryDao()) }
}