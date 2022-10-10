package stmik.mp.hafiz.antriandokter.data.api.queue

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface QueueAPI {
    @GET("bookings")
    suspend fun getAllBookings(
        @Header("Authorization") token: String,
    ): Response<AllBookingsResponse>

    @POST("patients/booking")
    suspend fun createBooking(
        @Header("Authorization") token: String,
        @Body request: CreateBookingRequest
    ) : Response<CreateBookingResponse>
}