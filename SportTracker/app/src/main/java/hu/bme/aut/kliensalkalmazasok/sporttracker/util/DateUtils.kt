package hu.bme.aut.kliensalkalmazasok.sporttracker.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateString(pattern: String = "yyyy-MM-dd"): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
}
