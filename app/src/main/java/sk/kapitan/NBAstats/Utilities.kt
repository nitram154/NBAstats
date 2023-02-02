package sk.kapitan.NBAstats

import android.icu.text.SimpleDateFormat
import java.text.ParseException
import java.util.*

fun parseDateToString(
    inputDateString: String?,
    inputDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US),
    outputDateFormat: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
): String? {
    val date: Date?
    var outputDateString: String? = null
    try {
        date = parseDate(inputDateString, inputDateFormat)
        outputDateString = outputDateFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return outputDateString
}

fun parseDate(
    inputDateString: String?,
    inputDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US),
): Date? {
    var date: Date? = null
    try {
        date = inputDateFormat.parse(inputDateString)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return date
}


fun dateToString(
    date: Date,
    outputDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
): String? {
    return outputDateFormat.format(date)
}