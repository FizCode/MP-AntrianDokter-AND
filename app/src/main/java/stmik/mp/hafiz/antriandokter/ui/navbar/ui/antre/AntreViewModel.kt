package stmik.mp.hafiz.antriandokter.ui.navbar.ui.antre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stmik.mp.hafiz.antriandokter.data.ErrorResponse
import stmik.mp.hafiz.antriandokter.data.api.auth.WhoAmIResponse
import stmik.mp.hafiz.antriandokter.data.api.queue.AllBookingsResponse
import stmik.mp.hafiz.antriandokter.data.api.queue.CreateBookingRequest
import stmik.mp.hafiz.antriandokter.repository.AuthRepository
import stmik.mp.hafiz.antriandokter.repository.ProfileRepository
import stmik.mp.hafiz.antriandokter.repository.QueueRepository
import javax.inject.Inject

@HiltViewModel
class AntreViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
    private val queueRepository: QueueRepository
) : ViewModel() {
    private var patientName: String = ""
    private var nik: String = ""
    private var bpjs: Int = 0

    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldShowProfile: MutableLiveData<WhoAmIResponse> = MutableLiveData()
    val shouldShowTicketData: MutableLiveData<AllBookingsResponse> = MutableLiveData()

    fun onChangePatientName(patientName: String) {
        this.patientName = patientName
    }
    fun onChangeNIK(nik: String) {
        this.nik = nik
    }
    fun onChangeBpjs(bpjs: Int) {
        this.bpjs = bpjs
    }

    fun onViewLoaded() {
        getProfile()
        // getAllBookings()
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
                    shouldShowTicketData.postValue(response.body())
                } else {
                    shouldShowError.postValue(response.errorBody().toString())
                }
            }
        }
    }

    fun onClickDaftar(){
        shouldShowLoading.postValue(true)
        processToAssign()
        println("Nama: $patientName \nNIK: $nik \nBPJS: $bpjs")
    }

    private fun processToAssign() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = authRepository.getToken().toString()
            val request = queueRepository.createBooking(
                token = "Bearer $token",
                CreateBookingRequest(
                    name = patientName,
                    patientNIK = nik,
                    examinationId = bpjs
                )
            )
            withContext(Dispatchers.Main) {
                if (request.isSuccessful) {
                    getAllBookings()
                } else {
                    val error =
                        Gson().fromJson(request.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
            shouldShowLoading.postValue(false)
        }
    }
}