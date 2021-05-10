package com.thesis.android_challenge_w6.data

import com.thesis.android_challenge_w6.model.User
import java.util.regex.Matcher
import java.util.regex.Pattern


class UserDataStore private constructor() {
    private val userList = ArrayList<User>()
    private lateinit var loginCallback: LoginCallback
    private lateinit var signUpCallback: SignUpCallback

    companion object {
        val instance = UserDataStore()

        const val FULL_NAME_FIELD = 0
        const val EMAIL_FIELD = 1
        const val PHONE_NUMBER_FIELD = 2

    }


    fun signUp(fullName: String, email: String, password: String) {
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            signUpCallback.onFailed("Field cannot empty")
        } else if (isInvalidEmail(email) || isInvalidPassword(password)){
            signUpCallback.onFailed("Email or password is in wrong format")
        } else {
            for (user in userList) {
                if (user.email == email) {
                    signUpCallback.onFailed("This email is already existed")
                    return
                }
            }
            val user = User(fullName, email, password)
            userList.add(user)
            signUpCallback.onSucceed()
        }
    }

    fun login(email: String, password: String) {
        for (user in userList) {
            if (user.email == email && user.password == password) {
                loginCallback.onSucceed(user)
                return
            }
        }
        loginCallback.onFailed("Please check your email or password")
    }

    fun getUserByEmail(email: String): User?{
        for(user in userList){
            if(user.email == email){
                return user
            }
        }
        return null
    }

    fun editUser(email: String, field: Int, value: String) {
        for (user in userList) {
            if (user.email == email) {
                when (field) {
                    FULL_NAME_FIELD -> {
                        user.fullName = value
                    }
                    EMAIL_FIELD -> {
                        user.email = value
                    }
                    PHONE_NUMBER_FIELD -> {
                        user.phoneNumber = value
                    }
                }
            }
        }
    }

    private fun isInvalidEmail(email: String): Boolean {
        val matcher: Matcher = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@gmail.com").matcher(email)
        return !matcher.matches()
    }

    private fun isInvalidPassword(password: String): Boolean{
        val matcher: Matcher =
            Pattern.compile("((?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#\$%^&*()]).{8,})").matcher(
                password
            )
        return !matcher.matches()
    }

    fun setSignUpCallback(signUpCallback: SignUpCallback) {
        this.signUpCallback = signUpCallback
    }

    fun setLoginCallback(loginCallback: LoginCallback) {
        this.loginCallback = loginCallback
    }

    interface LoginCallback {
        fun onSucceed(user: User)
        fun onFailed(message: String)

    }

    interface SignUpCallback {
        fun onSucceed()
        fun onFailed(message: String)
    }
}