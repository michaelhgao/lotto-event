package com.example.lottoevent.models

enum class EventState {
    DOES_NOT_EXIST,
    NOT_STARTED,
    OPEN_FOR_REG,
    CLOSED_FOR_REG,
    SELECTED_ENTRANTS,
    CONFIRMED_ENTRANTS,
    ENDED,
    CANCELLED,
}