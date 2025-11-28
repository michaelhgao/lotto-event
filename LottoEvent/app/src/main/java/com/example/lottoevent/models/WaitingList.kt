package com.example.lottoevent.models

class WaitingList(
    val waitingList: ArrayList<Pair<User, UserState>> = ArrayList(),
    var capacity: Int = -1,
) {

}