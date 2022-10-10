package stmik.mp.hafiz.antriandokter.model

import com.google.gson.annotations.SerializedName

data class CreateBookingModel(
    @SerializedName("name"          ) var name          : String? = null,
    @SerializedName("patientNIK"    ) var patientNIK    : String? = null,
    @SerializedName("examinationId" ) var examinationId : String? = null
)
