package com.example.videocallapp.login

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor():ViewModel() {
    private val loginState = MutableStateFlow<LoginState>(LoginState.Normal)
    val loginStateFlow=loginState.asStateFlow()
    fun login(userName:String, passWord:String){
        val auth = FirebaseAuth.getInstance()
        loginState.value = LoginState.Loading
        auth.signInWithEmailAndPassword(userName,passWord)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    if(user !=null){
                        loginState.value=LoginState.Success
                    }else{
                        loginState.value=LoginState.Error
                    }
                } else {
                    loginState.value=LoginState.Error
                }
            }
    }
}
sealed class  LoginState{
    object Normal:LoginState()
    object Loading:LoginState()
    object Success:LoginState()
    object Error:LoginState()


}