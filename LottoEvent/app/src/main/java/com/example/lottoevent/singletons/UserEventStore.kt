package com.example.lottoevent.singletons

import com.example.lottoevent.models.Event
import com.example.lottoevent.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object UserEventStore {
    private val _user = MutableStateFlow<User?>(null)
    private val _event = MutableStateFlow<Event?>(null)

    val user: StateFlow<User?> = _user.asStateFlow()
    val event: StateFlow<Event?> = _event.asStateFlow()

    fun getUser(): User? = _user.value
    fun setUser(user: User) {
        _user.update { user }
    }
    fun clearUser() {
        _user.update { null }
    }

    fun getEvent(): Event? = _event.value
    fun setEvent(event: Event) {
        _event.update { event }
    }
    fun clearEvent() {
        _event.update { null }
    }
}