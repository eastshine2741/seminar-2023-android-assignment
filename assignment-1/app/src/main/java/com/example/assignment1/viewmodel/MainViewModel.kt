package com.example.assignment1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.assignment1.InvalidPasswordError

class MainViewModel: ViewModel() {

    var userId: String = ""
    var userPw: String = ""
    fun login(id: String, pw: String) {
        if (pw.length < 5) {
            throw InvalidPasswordError()
        }
        userId = id
        userPw = pw
    }
}