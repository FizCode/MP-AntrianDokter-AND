package stmik.mp.hafiz.antriandokter.data.api.queue

import retrofit2.Response
import retrofit2.http.*

interface QueueAPI {
    @GET("bookings")
    suspend fun getAllBookings(
        @Header("Authorization") token: String,
    ): Response<AllBookingsResponse>

    @GET("bookings/{id}")
    suspend fun getBookingById(
        @Header("Authorization") token: String,
        @Path("id") bookingId: Int
    ) : Response<GetBookingByIdResponse>

    @POST("patients/booking")
    suspend fun createBooking(
        @Header("Authorization") token: String,
        @Body request: CreateBookingRequest
    ) : Response<CreateBookingResponse>
}