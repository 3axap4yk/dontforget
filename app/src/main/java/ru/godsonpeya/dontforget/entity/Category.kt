package ru.godsonpeya.dontforget.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val id: Int?=null,

    @NonNull
    @ColumnInfo(name = "name")
    var name: String
)