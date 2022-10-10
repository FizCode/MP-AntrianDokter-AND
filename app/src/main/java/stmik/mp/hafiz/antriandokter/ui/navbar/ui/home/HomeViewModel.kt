package stmik.mp.hafiz.antriandokter.ui.navbar.ui.home


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stmik.mp.hafiz.antriandokter.data.api.auth.WhoAmIResponse
import stmik.mp.hafiz.antriandokter.data.api.queue.AllBookingsResponse
import stmik.mp.hafiz.antriandokter.repository.AuthRepository
import stmik.mp.hafiz.antriandokter.repository.ProfileRepository
import stmik.mp.hafiz.antriandokter.repository.QueueRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val queueRepository: QueueRepository
) : ViewModel() {

    val shouldShowProfile: MutableLiveData<WhoAmIResponse> = MutableLiveData()
    val shouldShowAllBookings: MutableLiveData<AllBookingsResponse> = MutableLiveData()

    fun onViewLoaded() {
        getProfile()
        getAllBookings()
    }

    private fun getProfile() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = profileRepository.getProfile()
            withContext(Dispatchers.Main) {
                result.let {
                    shouldShowProfile.postValue(it)
                }
            }
        }
    }

    private fun getAllBookings() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = authRepository.getToken().toString()
            val response = queueRepository.getAllBookings("Bearer $token")
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    shouldShowAllBookings.postValue(response.body())
                }
            }
        }
    }
}