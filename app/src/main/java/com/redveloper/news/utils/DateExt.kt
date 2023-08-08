package com.redveloper.news.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun convertStrDateToDate(strDate: String): Date{
    val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputDateFormat = SimpleDateFormat("dd/MMMM/yyyy", Locale.getDefault())

    val inputDate = inputDateFormat.parse(strDate)
    return try {
        val ouputStrDate = outputDateFormat.format(inputDate)
        outputDateFormat.parse(ouputStrDate)
    } catch(e: Exception) {
        Date()
    }
}