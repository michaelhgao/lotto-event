package com.example.lottoevent.models

data class User(
    val id: String,
) {
    var name: String = "";
    var email: String = "";
    var phone: String = "";
    val events: List<Pair<Event, EventState>> = ArrayList();

    constructor(
        id: String,
        name: String,
        email: String,
        phone: String,
    ) : this(id) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    constructor() : this("")
}