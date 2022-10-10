package stmik.mp.hafiz.antriandokter.data.api.queue

import com.google.gson.annotations.SerializedName

data class CreateBookingResponse(
    @SerializedName("id"            ) var id            : Int?    = null,
    @SerializedName("patientId"     ) var patientId     : Int?    = null,
    @SerializedName("patientName"   ) var patientName   : String? = null,
    @SerializedName("patientNIK"    ) var patientNIK    : String? = null,
    @SerializedName("examinationId" ) var examinationId : Int?    = null,
    @SerializedName("dateOfVisit"   ) var dateOfVisit   : String? = null,
    @SerializedName("isDone"        ) var isDone        : String? = null,
    @SerializedName("queueNumber"   ) var queueNumber   : Int?    = null
)
