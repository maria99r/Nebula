package com.alanturing.nebula.viewModel.authentication


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturing.nebula.model.authentication.AuthRepository
import com.alanturing.nebula.model.authentication.AuthRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class ViewModelAuth : ViewModel() {

    private val repository = AuthRepository()


    fun login(email: String, password: String) {

        val authRequest = AuthRequest(email = email, password = password)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseService =
                    repository.login(authRequest)
                if (responseService.isSuccessful) {
                    delay(1500L)

                    responseService.body()?.let { tokenDto ->
                        Log.d("Logging", "Response TokenDto:$tokenDto")
                    }
                } else {
                    responseService.errorBody()?.let { error ->
                        delay(1500L)
                        error.close()
                    }
                }
            } catch (e: Exception) {
                Log.d("Logging", "Error Authentication", e)
            }
        }
    }
}


sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}