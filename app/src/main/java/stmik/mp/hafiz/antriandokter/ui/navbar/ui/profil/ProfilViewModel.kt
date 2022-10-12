package stmik.mp.hafiz.antriandokter.ui.navbar.ui.profil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import stmik.mp.hafiz.antriandokter.data.local.auth.UserEntity
import stmik.mp.hafiz.antriandokter.repository.AuthRepository
import stmik.mp.hafiz.antriandokter.repository.ProfileRepository
import javax.inject.Inject

@HiltViewModel
class ProfilViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel() {

    val shouldShowProfile: MutableLiveData<UserEntity> = MutableLiveData()
    val shouldOpenSignIn: MutableLiveData<Boolean> = MutableLiveData()
    val shouldShowError: MutableLiveData<String> = MutableLiveData()

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

    fun signOut() {
        viewModelScope.launch {
            authRepository.clearToken()
            profileRepository.deletUser()
            withContext(Dispatchers.Main) {
                shouldOpenSignIn.postValue(true)
            }
        }
    }
}