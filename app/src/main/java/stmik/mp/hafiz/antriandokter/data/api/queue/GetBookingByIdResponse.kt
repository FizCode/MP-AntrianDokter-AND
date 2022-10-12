package stmik.mp.hafiz.antriandokter.data.api.queue

import com.google.gson.annotations.SerializedName

data class GetBookingByIdResponse(
    @SerializedName("id"            ) var id            : Int?         = null,
    @SerializedName("patientId"     ) var patientId     : Int?         = null,
    @SerializedName("patientName"   ) var patientName   : String?      = null,
    @SerializedName("patientNIK"    ) var patientNIK    : String?      = null,
    @SerializedName("examinationId" ) var examinationId : Int?         = null,
    @SerializedName("dateOfVisit"   ) var dateOfVisit   : String?      = null,
    @SerializedName("queueNumber"   ) var queueNumber   : Int?         = null,
    @SerializedName("isDone"        ) var isDone        : Boolean?     = null,
    @SerializedName("createdAt"     ) var createdAt     : String?      = null,
    @SerializedName("updatedAt"     ) var updatedAt     : String?      = null,
    @SerializedName("examination"   ) var examination   : Examination? = Examination()
) {
    data class Examination (

        @SerializedName("name" ) var name : String? = null

    )
}