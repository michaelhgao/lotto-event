package com.example.lottoevent.models

data class User(
    val id: String,
) {
    var name: String = "";
    var email: String = "";
    var phone: String = "";
    var events: List<Pair<Event, EventState>> = ArrayList();

    constructor(
        id: String,
        name: String,
        email: String,
        phone: String,
        events: List<Pair<Event, EventState>>,
    ) : this(id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.events = events;
    }

    constructor() : this("")
}