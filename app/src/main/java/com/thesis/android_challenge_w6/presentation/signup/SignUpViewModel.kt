package com.thesis.android_challenge_w6.presentation.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thesis.android_challenge_w6.model.User
import com.thesis.android_challenge_w6.data.UserDataStore

class SignUpViewModel : ViewModel() {
    val user = MutableLiveData<User>()
    var isSignUpSucceed = MutableLiveData<Boolean>()
    var errorMessage = MutableLiveData<String>()

    init {
        user.value = User()
    }

    fun signUp() {
        val dataStore = UserDataStore.instance
        dataStore.setSignUpCallback(signUpCallback)
        dataStore.signUp(user.value!!.fullName, user.value!!.email, user.value!!.password)
    }

    fun clear(){
        isSignUpSucceed.value = null
        errorMessage.value = null
    }



    private val signUpCallback  = object : UserDataStore.SignUpCallback{
        override fun onSucceed() {
            isSignUpSucceed.value = true
        }

        override fun onFailed(message: String) {
            isSignUpSucceed.value = false
            errorMessage.value = message
        }
    }
}