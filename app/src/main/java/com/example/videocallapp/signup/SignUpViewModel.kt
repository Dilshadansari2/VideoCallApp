package com.example.videocallapp.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor():ViewModel() {
    private val loginState = MutableStateFlow<SignUpState>(SignUpState.Normal)
    val signUpState=loginState.asStateFlow()
    fun signUp(userName:String, passWord:String){
        val auth = FirebaseAuth.getInstance()
        loginState.value = SignUpState.Loading
        auth.createUserWithEmailAndPassword(userName,passWord)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    if(user !=null){
                        loginState.value=SignUpState.Success
                    }else{
                        loginState.value=SignUpState.Error
                    }
                } else {
                    loginState.value=SignUpState.Error
                }
            }
    }
}
sealed class  SignUpState{
    object Normal:SignUpState()
    object Loading:SignUpState()
    object Success:SignUpState()
    object Error:SignUpState()


}