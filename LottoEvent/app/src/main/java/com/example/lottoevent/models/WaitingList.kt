package com.example.lottoevent.models

import com.example.lottoevent.exceptions.EventFullException
import com.example.lottoevent.exceptions.UserInWaitingListException
import com.example.lottoevent.exceptions.UserNotInWaitingListException

data class WaitingListUser(
    var user: User? = null,
    var state: UserState? = UserState.NOT_IN,
)

class WaitingList(
    val waitingList: MutableList<WaitingListUser> = ArrayList(),
    var capacity: Int = -1,
) {
    fun addUser(user: User, state: UserState) {
        if (hasUser(user)) {
            throw UserInWaitingListException("User with ID ${user.id} is already in the waiting list.")
        }
        if (isFull()) {
            throw EventFullException("Cannot add user. The event waiting list has reached its maximum capacity of ${capacity}.")
        }
        waitingList.add(WaitingListUser(user = user, state = state))
    }

    fun removeUser(user: User) {
        val u = waitingList.find { it.user?.id == user.id }
        if (u == null) {
            throw UserNotInWaitingListException("User with ID ${user.id} is not in the waiting list.")
        }
        waitingList.remove(u)
    }

    fun updateUserState(user: User, newState: UserState) {
        val index = waitingList.indexOfFirst { it.user?.id == user.id }
        if (index == -1) {
            throw UserNotInWaitingListException("User with ID ${user.id} is not in the waiting list.")
        }
        waitingList[index].state = newState
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