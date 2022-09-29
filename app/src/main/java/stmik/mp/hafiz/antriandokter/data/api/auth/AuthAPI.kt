package stmik.mp.hafiz.antriandokter.data.api.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthAPI {
    @POST("patients/login")
    suspend fun signIn(@Body request: SignInRequest): Response<SignInResponse>

    @POST("patients/register")
    suspend fun signUp(@Body request: SignUpRequest): Response<SignUpResponse>

    @GET("patients/who-am-i")
    suspend fun getUserData(
        @Header("Authorization") token: String,
    ): Response<WhoAmIResponse>

    @PUT("patients/{id}/detail")
    suspend fun updateUserData(
        @Body request: UpdateUserDataRequest,
        @Path("id") id: Int,
        @Header("Authorization") token: String
    ): Response<UpdateUserDataResponse>
}