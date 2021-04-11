package ru.godsonpeya.dontforget.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import ru.godsonpeya.dontforget.entity.Category
import ru.godsonpeya.dontforget.entity.Parameter
import ru.godsonpeya.dontforget.local.dao.CategoryDao
import ru.godsonpeya.dontforget.local.dao.ParameterDao


@Database(entities = [Category::class, Parameter::class], version = 2, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun parameterDao(): ParameterDao

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "dont_forget_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}