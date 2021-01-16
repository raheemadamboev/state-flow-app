package xyz.teamgravity.stateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUIState>(LoginUIState.Empty)
    val loginUIState: StateFlow<LoginUIState> = _loginState

    // simulate login process
    fun login(username: String, password: String) = viewModelScope.launch {
        _loginState.value = LoginUIState.Loading
        // fake network request time
        delay(2000L)
        if (username == "raheem" && password == "android") {
            _loginState.value = LoginUIState.Success
        } else {
            _loginState.value = LoginUIState.Error("Incorrect password")
        }
    }

    // login ui states
    sealed class LoginUIState {
        object Success : LoginUIState()
        data class Error(val message: String) : LoginUIState()
        object Loading : LoginUIState()
        object Empty : LoginUIState()
    }
}