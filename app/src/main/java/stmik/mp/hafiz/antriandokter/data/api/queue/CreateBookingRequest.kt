package stmik.mp.hafiz.antriandokter.data.api.queue

import com.google.gson.annotations.SerializedName

data class CreateBookingRequest(
    @SerializedName("name"          ) var name          : String? = null,
    @SerializedName("patientNIK"    ) var patientNIK    : String? = null,
    @SerializedName("examinationId" ) var examinationId : Int?    = null
)
