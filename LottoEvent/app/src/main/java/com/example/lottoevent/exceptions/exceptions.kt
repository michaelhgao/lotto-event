package com.example.lottoevent.exceptions

class UserInWaitingListException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

class UserNotInWaitingListException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)

class EventFullException(
    message: String,
    cause: Throwable? = null
) : RuntimeException(message, cause)