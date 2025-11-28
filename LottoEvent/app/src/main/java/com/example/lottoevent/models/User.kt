package com.example.lottoevent.models

data class UserEvent(
    val event: Event? = null,
    val state: EventState? = EventState.DOES_NOT_EXIST,
)

data class User(
    val id: String,
    var name: String = "",
    var email: String = "",
    var phone: String = "",
) {
    var events: MutableList<UserEvent> = ArrayList();

    constructor() : this("")

}