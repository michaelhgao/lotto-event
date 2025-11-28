package com.example.lottoevent.models

class Event(
    val id: String,
    var title: String,
    var description: String,
    var capacity: Int,
    val organizer: User,
    val waitingList: WaitingList,
) {
    constructor() : this("", "", "", 0, User(), WaitingList())
}