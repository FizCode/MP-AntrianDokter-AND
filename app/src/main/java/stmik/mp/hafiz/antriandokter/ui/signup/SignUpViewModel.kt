package stmik.mp.hafiz.antriandokter.ui.signup

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stmik.mp.hafiz.antriandokter.data.ErrorResponse
import stmik.mp.hafiz.antriandokter.data.api.auth.SignUpRequest
import stmik.mp.hafiz.antriandokter.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private var name: String = ""
    private var nik: String = ""
    private var email: String = ""
    private var DoB: String = ""
    private var address: String = ""
    private var gender: String = ""
    private var password: String = ""

    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()
    val shouldOpenSignIn: MutableLiveData<Boolean> = MutableLiveData()

    fun onChangeName(name: String) {
        this.name = name
    }
    fun onChangeNIK(nik: String) {
        this.nik = nik
    }
    fun onChangeEmail(email: String) {
        this.email = email
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
    fun onChangePassword(password: String) {
        this.password = password
    }

    fun onClickSignUp() {
        shouldShowLoading.postValue(true)
        processToSignUp()
    }


    private fun processToSignUp() {
        CoroutineScope(Dispatchers.IO).launch {
            val request = SignUpRequest(
                name = name,
                NIK = nik,
                email = email,
                dateOfBirth = DoB,
                address = address,
                gender = gender,
                password = password
            )
            val result = authRepository.signUp(request = request)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    shouldOpenSignIn.postValue(true)
                } else {
                    val error =
                        Gson().fromJson(result.errorBody()?.string(), ErrorResponse::class.java)
                    shouldShowError.postValue(error.message.orEmpty() + " #${error.code}")
                }
                shouldShowLoading.postValue(false)
            }
        }
    }
}