package com.example.lottoevent.models

data class Event(
    val id: String,
) {
    var title: String = "";
    var description: String = "";
    var capacity: Int = -1;
    var organizer: User = User();
    val waitingList: WaitingList = WaitingList();

    constructor(
        id: String,
        title: String,
        description: String,
        capacity: Int,
        organizer: User,
    ) : this(id) {
        this.title = title
        this.description = description
        this.capacity = capacity
        this.organizer = organizer
    }

    constructor() : this("")
}