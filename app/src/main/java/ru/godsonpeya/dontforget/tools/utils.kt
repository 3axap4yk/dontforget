package ru.godsonpeya.dontforget.tools

import android.text.Editable
import java.text.SimpleDateFormat

fun formatDate(longMills: Long):String{
    val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
    val dateString = simpleDateFormat.format(longMills)
    return String.format(dateString)
}

fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
