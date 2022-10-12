package stmik.mp.hafiz.antriandokter.ui.ticket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stmik.mp.hafiz.antriandokter.data.ErrorResponse
import stmik.mp.hafiz.antriandokter.data.api.queue.GetBookingByIdResponse
import stmik.mp.hafiz.antriandokter.repository.AuthRepository
import stmik.mp.hafiz.antriandokter.repository.ProfileRepository
import stmik.mp.hafiz.antriandokter.repository.QueueRepository
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val queueRepository: QueueRepository
) : ViewModel() {

    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldShowTicketData: MutableLiveData<GetBookingByIdResponse> = MutableLiveData()

    fun onViewLoaded() {
        shouldShowLoading.postValue(true)
        getBookingTicket()
    }

    private fun getBookingTicket() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = authRepository.getToken().toString()
            val bookingId = profileRepository.getProfile().bookingId

            val response = queueRepository.getBookingById(
                token = "Bearer $token",
                bookingId = bookingId.hashCode()
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful && response.body()?.isDone == false) {
                    shouldShowTicketData.postValue(response.body())
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
            shouldShowLoading.postValue(false)
        }
    }
}