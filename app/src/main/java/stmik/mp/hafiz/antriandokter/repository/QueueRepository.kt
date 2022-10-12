package stmik.mp.hafiz.antriandokter.repository

import retrofit2.Response
import stmik.mp.hafiz.antriandokter.data.api.queue.*
import javax.inject.Inject

class QueueRepository @Inject constructor(
    private val api: QueueAPI
) {
    suspend fun getAllBookings(token: String): Response<AllBookingsResponse> {
        return api.getAllBookings(token = token)
    }

    suspend fun getBookingById(token: String, bookingId: Int): Response<GetBookingByIdResponse> {
        return api.getBookingById(token = token, bookingId = bookingId)
    }

    suspend fun createBooking(token: String, request: CreateBookingRequest): Response<CreateBookingResponse> {
        return api.createBooking(token = token, request = request)
    }

}