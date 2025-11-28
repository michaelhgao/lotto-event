package com.example.lottoevent.models

class WaitingList(
    val waitingList: ArrayList<Pair<User, UserState>> = ArrayList(),
    var capacity: Int = -1,
) {
    constructor() : this(ArrayList(), -1)

    fun addUser(user: User, state: UserState) {
        waitingList.add(Pair(user, state));
    }

    fun removeUser(user: User): Boolean {
        val e = waitingList.find { it.first == user }

        if (e != null) {
            return waitingList.remove(e)
        }

        return false
    }

    fun updateUserState(user: User, newState: UserState): Boolean {
        val index = waitingList.indexOfFirst { it.first == user }

        if (index != -1) {
            waitingList[index] = user to newState
            return true
        }
        return false
    }

    fun hasUser(user: User): Boolean {
        return waitingList.any { it.first == user }
    }

    fun getSize(): Int {
        return waitingList.size
    }

    fun isFull(): Boolean {
        return capacity != -1 && waitingList.size >= capacity
    }

}