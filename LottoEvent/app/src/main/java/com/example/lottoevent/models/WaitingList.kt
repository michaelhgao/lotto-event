package com.example.lottoevent.models

data class WaitingListUser(
    var user: User? = null,
    var state: UserState? = UserState.NOT_IN,
)

class WaitingList(
    val waitingList: MutableList<WaitingListUser> = ArrayList(),
    var capacity: Int = -1,
) {
    fun addUser(user: User, state: UserState) {
        waitingList.add(WaitingListUser(user = user, state = state));
    }

    fun removeUser(user: User): Boolean {
        val u = waitingList.find { it.user?.id == user.id }

        if (u != null) {
            return waitingList.remove(u)
        }
        return false
    }

    fun updateUserState(user: User, newState: UserState): Boolean {
        val index = waitingList.indexOfFirst { it.user?.id == user.id }

        if (index != -1) {
            waitingList[index].state = newState
            return true
        }
        return false
    }

    fun hasUser(user: User): Boolean {
        return waitingList.any { it.user?.id == user.id }
    }

    fun getSize(): Int {
        return waitingList.size
    }

    fun isFull(): Boolean {
        return capacity != -1 && waitingList.size >= capacity
    }

}