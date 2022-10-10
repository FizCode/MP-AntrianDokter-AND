package stmik.mp.hafiz.antriandokter.repository

import retrofit2.Response
import stmik.mp.hafiz.antriandokter.data.api.queue.AllBookingsResponse
import stmik.mp.hafiz.antriandokter.data.api.queue.CreateBookingRequest
import stmik.mp.hafiz.antriandokter.data.api.queue.CreateBookingResponse
import stmik.mp.hafiz.antriandokter.data.api.queue.QueueAPI
import stmik.mp.hafiz.antriandokter.datastore.AuthDataStoreManager
import javax.inject.Inject

class QueueRepository @Inject constructor(
    private val api: QueueAPI
) {
    suspend fun getAllBookings(token: String): Response<AllBookingsResponse> {
        return api.getAllBookings(token = token)
    }

    suspend fun createBooking(token: String, request: CreateBookingRequest) : Response<CreateBookingResponse> {
        return api.createBooking(token = token, request = request)
    }
}