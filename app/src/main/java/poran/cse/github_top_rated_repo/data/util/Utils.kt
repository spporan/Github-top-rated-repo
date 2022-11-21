package poran.cse.github_top_rated_repo.data.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDateTime(dateString: String, format: String = "yyyy-MM-dd hh:mm:ss a"):String? {
    val date = "2019-07-14T18:30:00.000Z"
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val outputFormat = SimpleDateFormat(format,  Locale.US)
    val parsedDate: Date? = inputFormat.parse(date)
    val formattedDate: String? = parsedDate?.let { outputFormat.format(it) }
    println(formattedDate)
    return formattedDate
}