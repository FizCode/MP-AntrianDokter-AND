package stmik.mp.hafiz.antriandokter.data.local.auth

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey var id: Int?,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "email") var email: String? = null,
    @ColumnInfo(name = "password") var password: String? = null,
    @ColumnInfo(name = "dateOfBirth") var dob: String? = null,
    @ColumnInfo(name = "address") var address: String? = null,
    @ColumnInfo(name = "gender") var gender: String? = null,
    @ColumnInfo(name = "NIK") var nik: String? = null,
    @ColumnInfo(name = "BPJS") var BPJS: String? = null,
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String? = null
)