package sk.kapitan.NBAstats

import android.icu.text.SimpleDateFormat
import java.text.ParseException
import java.util.*

fun parseDate(
    inputDateString: String?,
    inputDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US),
    outputDateFormat: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
): String? {
    var date: Date? = null
    var outputDateString: String? = null
    try {
        date = inputDateFormat.parse(inputDateString)
        outputDateString = outputDateFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return outputDateString

}

fun dateToString(
    date: Date,
    outputDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
): String? {
    return outputDateFormat.format(date)
}