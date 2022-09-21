package stmik.mp.hafiz.antriandokter.ui.signup

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    // repo
): ViewModel() {
    private var name: String = ""
    private var nik: String = ""
    private var email: String = ""
    private var DoB: String = ""
    private var address: String = ""
    private var gender: String = ""
    private var password: String = ""

    val shouldShowLoading: MutableLiveData<Boolean> = MutableLiveData()
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
    }


    fun processToSignUp() {
        CoroutineScope(Dispatchers.IO).launch {
            shouldShowLoading.postValue(true)
            // request, result, execute
        }
    }
}