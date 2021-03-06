package ru.godsonpeya.dontforget.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "parameter")
@Parcelize
data class Parameter(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String? = null,
    var value: String? = null,
    @ColumnInfo(name="category_id")
    var categoryId: Int? = null
) : Parcelable
