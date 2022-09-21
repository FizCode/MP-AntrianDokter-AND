package stmik.mp.hafiz.antriandokter.data.api.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthAPI {
    @POST("login")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>
}