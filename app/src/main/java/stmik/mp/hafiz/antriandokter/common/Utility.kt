package stmik.mp.hafiz.antriandokter.common

import java.text.SimpleDateFormat
import java.util.*

object ChangeDateFormat{
    private val indonesia = Locale("in", "ID")
    fun changeDate(date: String): String {

        // change text 2022-10-15 to be 15-10-2022
        val splitDate = date.split("-")
        val splittedDate = "${splitDate[2].trim()}-${splitDate[1].trim()}-${splitDate[0].trim()}"

        // chance Date Format to be 15-10-2022
        val dateConversion = SimpleDateFormat("dd-MM-yyyy").parse(splittedDate)
        // change Date Format to be 15 Oktober 2022
        val dateFormat = dateConversion?.let { SimpleDateFormat("dd MMMM yyyy", indonesia).format(it) }

        return dateFormat!!
    }

    fun changeDay(day: String): String {

        // change text 2022-10-15 to be 15-10-2022
        val splitDate = day.split("-")
        val splittedDate = "${splitDate[2].trim()}-${splitDate[1].trim()}-${splitDate[0].trim()}"

        // chance Date Format to be 15-10-2022
        val dateConversion = SimpleDateFormat("dd-MM-yyyy").parse(splittedDate)

        // change Date Format to be Senin
        val dayFormat = dateConversion?.let { SimpleDateFormat("EEEE", indonesia).format(it) }

        return dayFormat!!
    }

}
