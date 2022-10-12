package stmik.mp.hafiz.antriandokter.ui.navbar.ui.antre

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stmik.mp.hafiz.antriandokter.data.ErrorResponse
import stmik.mp.hafiz.antriandokter.data.api.queue.CreateBookingRequest
import stmik.mp.hafiz.antriandokter.data.api.queue.GetBookingByIdResponse
import stmik.mp.hafiz.antriandokter.data.local.auth.UserEntity
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
    val shouldShowProfile: MutableLiveData<UserEntity> = MutableLiveData()
    val shouldShowTicketData: MutableLiveData<GetBookingByIdResponse> = MutableLiveData()
    val shouldOpenTicket: MutableLiveData<Boolean> = MutableLiveData()
    val shouldOpenTicketPage: MutableLiveData<Boolean> = MutableLiveData()

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
        shouldShowLoading.postValue(true)
        getProfile()
    }

    private fun getProfile() {
        CoroutineScope(Dispatchers.Main).launch {
            val result = profileRepository.getProfile()
            withContext(Dispatchers.Main) {
                result.let {
                    shouldShowProfile.postValue(it)
                    getBookingTicket()
                }
            }
        }
    }

    fun onClickDaftar() {
        shouldShowLoading.postValue(true)
        processToAssign()
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
                    val createBookingResponse = request.body()
                    createBookingResponse?.let {
                        it.booking?.data?.id?.let { id -> insertUser(bookingId = id) }
                    }
                } else {
                    val error =
                        Gson().fromJson(request.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
        }
    }

    private fun insertUser(bookingId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val userEntity = profileRepository.getProfile()
            val result = authRepository.insertUser(
                userEntity = UserEntity(
                    id = userEntity.id,
                    name = userEntity.name.orEmpty(),
                    email = userEntity.email.orEmpty(),
                    password = userEntity.password.orEmpty(),
                    dateOfBirth = userEntity.dateOfBirth.orEmpty(),
                    address = userEntity.address.orEmpty(),
                    gender = userEntity.gender.orEmpty(),
                    NIK = userEntity.NIK.orEmpty(),
                    phoneNumber = userEntity.phoneNumber.orEmpty(),
                    bookingId = bookingId
                )
            )
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    shouldOpenTicketPage.postValue(true)
                } else {
                    shouldShowError.postValue("Gagal insert data Booking ID ke dalam sistem.")
                }
            }
        }
    }

    private fun getBookingTicket() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = authRepository.getToken().toString()
            val bookingId = profileRepository.getProfile().bookingId

            val response = queueRepository.getBookingById(
                token = "Bearer $token",
                bookingId = bookingId.hashCode()
            )

            if (bookingId == null) {
                shouldOpenTicket.postValue(false)
            } else {
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body()?.isDone == false) {
                        shouldOpenTicket.postValue(true)
                        shouldShowTicketData.postValue(response.body())
                    } else {
                        shouldOpenTicket.postValue(false)
                    }
                }
            }
            shouldShowLoading.postValue(false)
        }
    }
}