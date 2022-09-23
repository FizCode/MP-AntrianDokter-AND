package stmik.mp.hafiz.antriandokter.data.api.auth

import com.google.gson.annotations.SerializedName

data class SignUpResponse (
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("email"       ) var email       : String? = null,
    @SerializedName("dateOfBirth" ) var dateOfBirth : String? = null,
    @SerializedName("address"     ) var address     : String? = null,
    @SerializedName("gender"      ) var gender      : String? = null,
    @SerializedName("image"       ) var image       : String? = null,
    @SerializedName("NIK"         ) var NIK         : String? = null,
    @SerializedName("phoneNumber" ) var phoneNumber : String? = null
)