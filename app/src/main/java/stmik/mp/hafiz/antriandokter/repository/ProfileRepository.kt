package stmik.mp.hafiz.antriandokter.repository

import stmik.mp.hafiz.antriandokter.data.local.auth.UserDAO
import stmik.mp.hafiz.antriandokter.data.local.auth.UserEntity
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val dao: UserDAO
) {

    suspend fun getProfile(): UserEntity {
        return dao.getUser().let {
            UserEntity(
                id = it?.id.hashCode(),
                name = it?.name.orEmpty(),
                email = it?.email.orEmpty(),
                password = it?.password.orEmpty(),
                dateOfBirth = it?.dateOfBirth.orEmpty(),
                address = it?.address.orEmpty(),
                gender = it?.gender.orEmpty(),
                NIK = it?.NIK.orEmpty(),
                phoneNumber = it?.phoneNumber.orEmpty(),
                bookingId = it?.bookingId.hashCode()
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
            dateOfBirth = profile?.dateOfBirth.orEmpty(),
            address = profile?.address.orEmpty(),
            gender = profile?.gender.orEmpty(),
            NIK = profile?.NIK.orEmpty(),
            phoneNumber = profile?.phoneNumber.orEmpty()
        )
        return dao.removeUser(removeProfile)
    }
}