package stmik.mp.hafiz.antriandokter.common

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "dd-MM-yyyy"

private val customDateFormat: String = SimpleDateFormat(
    DATE_FORMAT,
    Locale.US
).format(System.currentTimeMillis())
