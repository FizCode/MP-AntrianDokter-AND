package stmik.mp.hafiz.antriandokter.model

import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("id"          ) var id          : Int?    = null,
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("email"       ) var email       : String? = null,
    @SerializedName("password"    ) var password    : String? = null,
    @SerializedName("dateOfBirth" ) var dateOfBirth : String? = null,
    @SerializedName("address"     ) var address     : String? = null,
    @SerializedName("gender"      ) var gender      : String? = null,
    @SerializedName("NIK"         ) var NIK         : String? = null,
    @SerializedName("BPJS"        ) var BPJS        : String? = null,
    @SerializedName("phoneNumber" ) var phoneNumber : String? = null
)