package ru.godsonpeya.dontforget.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithParameter(
    @Embedded val category : Category?= null,

    @Relation(
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val parameters: List<Parameter> = mutableListOf()
)