package com.example.lottoevent.models

data class Event(
    val id: String = "",
    var title: String = "",
    var description: String = "",
    var capacity: Int = -1,
    var organizer: User = User(),
    var waitingList: WaitingList = WaitingList(),
) {

}