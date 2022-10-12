package stmik.mp.hafiz.antriandokter.data.api.queue

import com.google.gson.annotations.SerializedName

data class CreateBookingResponse(
    @SerializedName("message" ) var message : String?  = null,
    @SerializedName("booking" ) var booking : Booking? = Booking()
) {
    data class Booking (
        @SerializedName("data"  ) var data  : Data? = Data(),
        @SerializedName("count" ) var count : Int?  = null
    )

    data class Data (
        @SerializedName("id"            ) var id            : Int?     = null,
        @SerializedName("patientId"     ) var patientId     : Int?     = null,
        @SerializedName("patientName"   ) var patientName   : String?  = null,
        @SerializedName("patientNIK"    ) var patientNIK    : String?  = null,
        @SerializedName("examinationId" ) var examinationId : Int?     = null,
        @SerializedName("dateOfVisit"   ) var dateOfVisit   : String?  = null,
        @SerializedName("isDone"        ) var isDone        : Boolean? = null,
        @SerializedName("updatedAt"     ) var updatedAt     : String?  = null,
        @SerializedName("createdAt"     ) var createdAt     : String?  = null,
        @SerializedName("queueNumber"   ) var queueNumber   : Int?     = null
    )
}
