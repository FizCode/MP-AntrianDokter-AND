package stmik.mp.hafiz.antriandokter.ui.editprofil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stmik.mp.hafiz.antriandokter.data.ErrorResponse
import stmik.mp.hafiz.antriandokter.data.api.auth.UpdateUserDataRequest
import stmik.mp.hafiz.antriandokter.data.local.auth.UserEntity
import stmik.mp.hafiz.antriandokter.repository.AuthRepository
import stmik.mp.hafiz.antriandokter.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class EditProfilViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
): ViewModel() {

    private var name: String = ""
    private var nik: String = ""
    private var DoB: String = ""
    private var address: String = ""
    private var gender: String = ""

    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowProfile: MutableLiveData<UserEntity> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldShowSuccess: MutableLiveData<String> = MutableLiveData()

    fun onChangeName(name: String) {
        this.name = name
    }
    fun onChangeNIK(nik: String) {
        this.nik = nik
    }
    fun onChangeDoB(DoB: String) {
        this.DoB = DoB
    }
    fun onChangeAddress(address: String) {
        this.address = address
    }
    fun onChangeGender(gender: String) {
        this.gender = gender
    }

    fun onClickUpdate() {
        shouldShowLoading.postValue(true)
        updateUserData()
    }

    fun onViewLoaded() {
        getProfile()
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

    private fun updateUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = authRepository.getToken().toString()
            val userDataId = profileRepository.getProfile().id
            val result = userDataId.let {
                authRepository.updateUserData(
                    request = UpdateUserDataRequest(
                        name = name,
                        NIK = nik,
                        dateOfBirth = DoB,
                        address = address,
                        gender = gender
                    ),
                    token = "Bearer $token",
                    id = it
                )
            }
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    getUserData(token = "Bearer $token")
                } else {
                    shouldShowError.postValue(result.errorBody().toString())
                }
            }
            shouldShowLoading.postValue(false)
        }
    }

    private fun getUserData(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = authRepository.getUser(token = token)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val getUserDataResponse = response.body()
                    getUserDataResponse?.let {
                        val userEntity = UserEntity(
                            id = it.id.hashCode(),
                            name = it.name.orEmpty(),
                            email = it.email.orEmpty(),
                            password = it.password.orEmpty(),
                            dateOfBirth = it.dateOfBirth.orEmpty(),
                            address = it.address.orEmpty(),
                            gender = it.gender.orEmpty(),
                            NIK = it.NIK.orEmpty(),
                            phoneNumber = it.phoneNumber.orEmpty()
                        )
                        insertProfile(userEntity = userEntity)
                    }
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
            }
        }
    }

    private fun insertProfile(userEntity: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = authRepository.insertUser(userEntity)
            withContext(Dispatchers.Main) {
                if (result != 0L) {
                    shouldShowSuccess.postValue("Profil berhasil diupdate!")
                } else {
                    shouldShowError.postValue("Maaf, gagal insert ke dalam database!")
                }
            }
        }
    }
}