package com.example.lottoevent.viewmodels

import androidx.lifecycle.ViewModel
import com.example.lottoevent.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UserEventViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)

    val user: StateFlow<User?> = _user.asStateFlow()

    fun getUser(): User? = _user.value
    fun setUser(user: User) {
        _user.update { user }
    }
    fun clearUser() {
        _user.update { null }
    }


}