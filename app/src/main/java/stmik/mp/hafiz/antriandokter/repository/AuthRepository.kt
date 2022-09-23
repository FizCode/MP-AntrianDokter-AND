package stmik.mp.hafiz.antriandokter.repository

import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Response
import stmik.mp.hafiz.antriandokter.data.api.auth.*
import stmik.mp.hafiz.antriandokter.data.local.auth.UserDAO
import stmik.mp.hafiz.antriandokter.data.local.auth.UserEntity
import stmik.mp.hafiz.antriandokter.datastore.AuthDataStoreManager
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authDataStore: AuthDataStoreManager,
    private val api: AuthAPI,
    private val dao: UserDAO
) {
    suspend fun getToken(): String? {
        return authDataStore.getToken().firstOrNull()
    }

    suspend fun updateToken(value: String) {
        authDataStore.setToken(value)
    }
    suspend fun clearToken() {
        updateToken("")
    }

    suspend fun signIn(request: SignInRequest): Response<SignInResponse> {
        return api.signIn(request)
    }

    suspend fun signUp(request: SignUpRequest): Response<SignUpResponse> {
        return api.signUp(request)
    }

    suspend fun getUser(token: String): Response<WhoAmIResponse> {
        return api.getUserData(token = token)
    }

    suspend fun insertUser(userEntity: UserEntity): Long {
        return dao.insertUser(userEntity)
    }

}