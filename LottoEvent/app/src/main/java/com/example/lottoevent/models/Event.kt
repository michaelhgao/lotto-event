package com.example.lottoevent.models

data class Event(
    val id: String,
) {
    var title: String = "";
    var description: String = "";
    var capacity: Int = 0;
    lateinit var organizer: User;
    lateinit var waitingList: WaitingList;

    constructor(
        id: String,
        title: String,
        description: String,
        capacity: Int,
        organizer: User,
        waitingList: WaitingList = WaitingList(),
    ) : this(id) {
        this.title = title
        this.description = description
        this.capacity = capacity
        this.organizer = organizer
        this.waitingList = waitingList
    }

    constructor() : this("")
}