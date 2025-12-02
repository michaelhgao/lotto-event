package com.example.lottoevent.models

/**
 * An event. Holds details about a lottery-style event, including the organizer,
 * participant capacity, and a list of users currently on the waiting list.
 *
 * @constructor Creates an Event object with default or specified values.
 * @property id A unique identifier for the event.
 * @property title The name of the event.
 * @property description A description of the event.
 * @property capacity The maximum number of attendees.
 * @property organizer The [User] who created the event.
 * @property waitingList The [WaitingList] managing users waiting to attend.
 * @author Michael
 * @since 1.0.0
 * @see [User]
 * @see [WaitingList]
 */
data class Event(
    val id: String = "",
    var title: String = "",
    var description: String = "",
    var capacity: Int = -1,
    var organizer: User = User(),
    var waitingList: WaitingList = WaitingList(),
) {

}