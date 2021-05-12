import java.text.SimpleDateFormat
import java.util.Date

object Utils {
    fun timestampVersionCode(): Int {
        val dateStampInt = Integer.parseInt(SimpleDateFormat("yyyyMMddHH").format(Date()))
        println("\nDatestamp version code generated: $dateStampInt\n")
        return dateStampInt
    }
}