package com.example.lottoevent.models

class User(
    val id: String,
    var name: String,
    var email: String,
    var phone: String,
    var events: List<Pair<Event, EventState>> = ArrayList(),
) {
    constructor() : this("", "", "", "")
}