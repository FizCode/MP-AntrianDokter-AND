package stmik.mp.hafiz.antriandokter.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stmik.mp.hafiz.antriandokter.data.ErrorResponse
import stmik.mp.hafiz.antriandokter.data.api.auth.SignInRequest
import stmik.mp.hafiz.antriandokter.data.local.auth.UserEntity
import stmik.mp.hafiz.antriandokter.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private var email: String = ""
    private var password: String = ""

    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldOpenMenuPage: MutableLiveData<Boolean> = MutableLiveData()

    fun onChangeEmail(email: String) {
        this.email = email
    }
    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onClickSignIn() {
        shouldShowLoading.postValue(true)
        signInFromAPI()
    }

    private fun signInFromAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = SignInRequest(
                email = email,
                password = password
            )
            val response = authRepository.signIn(request)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val signInResponse = response.body()
                    signInResponse?.let {
                        val token = it.token.orEmpty()
                        // insert token & get User data
                        insertToken(token = token)
                        getUserData(token = token)
                    }
                } else {
                    val error =
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
                shouldShowLoading.postValue(false)
            }
        }
    }

    private fun insertToken(token: String) {
        if (token.isNotEmpty()) {
            viewModelScope.launch {
                authRepository.updateToken(token)
            }
        }
    }

    private fun getUserData(token: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = authRepository.getUser(token = "Bearer $token")
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
                    shouldOpenMenuPage.postValue(true)
                } else {
                    shouldShowError.postValue("Maaf, gagal insert ke dalam database!")
                }
            }
        }
    }
}