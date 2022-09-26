package stmik.mp.hafiz.antriandokter.repository

import stmik.mp.hafiz.antriandokter.data.api.auth.WhoAmIResponse
import stmik.mp.hafiz.antriandokter.data.local.auth.UserDAO
import stmik.mp.hafiz.antriandokter.data.local.auth.UserEntity
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val dao: UserDAO
) {

    suspend fun getProfile(): WhoAmIResponse {
        return dao.getUser().let {
            WhoAmIResponse(
                id = it?.id.hashCode(),
                name = it?.name.orEmpty(),
                email = it?.email.orEmpty(),
                password = it?.password.orEmpty(),
                dateOfBirth = it?.dob.orEmpty(),
                address = it?.address.orEmpty(),
                gender = it?.gender.orEmpty(),
                image = it?.image.orEmpty(),
                NIK = it?.nik.orEmpty(),
                BPJS = it?.BPJS.orEmpty(),
                phoneNumber = it?.phoneNumber.orEmpty()
            )
        }
    }

    suspend fun deletUser(): Int {
        val profile = dao.getUser()
        val removeProfile = UserEntity(
            id = profile?.id.hashCode(),
            name = profile?.name.orEmpty(),
            email = profile?.email.orEmpty(),
            password = profile?.password.orEmpty(),
            dob = profile?.dob.orEmpty(),
            address = profile?.address.orEmpty(),
            gender = profile?.gender.orEmpty(),
            image = profile?.image.orEmpty(),
            nik = profile?.nik.orEmpty(),
            BPJS = profile?.BPJS.orEmpty(),
            phoneNumber = profile?.phoneNumber.orEmpty()
        )
        return dao.removeUser(removeProfile)
    }
}