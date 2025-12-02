package com.example.lottoevent

import com.example.lottoevent.exceptions.EventFullException
import com.example.lottoevent.exceptions.UserInWaitingListException
import com.example.lottoevent.models.User
import com.example.lottoevent.models.UserState
import com.example.lottoevent.models.WaitingList
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WaitingListTest {
    private val user1 = User("id1", "name1", "email1", "phone1")
    private val user2 = User("id2", "name2", "email2", "phone2")
    private val user3 = User("id3", "name3", "email3", "phone3")
    private lateinit var waitingList: WaitingList

    @Before
    fun setUp() {
        waitingList = WaitingList()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `addUser should increase size by one`() {
        val initialSize = waitingList.getSize()

        waitingList.addUser(user1, UserState.ENTERED)

        assertEquals("Initial size did not increase by one", initialSize + 1, waitingList.getSize())
    }

    @Test
    fun `addUser should add user with correct state`() {
        waitingList.addUser(user2, UserState.ACCEPTED)

        val addedUser = waitingList.waitingList.find { it.user?.id == user2.id }
        assertEquals("ID does not match", user2.id, addedUser?.user?.id)
        assertEquals("State does not match", UserState.ACCEPTED, addedUser?.state)
    }

    @Test
    fun `addUser should not add user already in list`() {
        waitingList.addUser(user1, UserState.ENTERED);

        assertEquals(1, waitingList.getSize())

        assertThrows(UserInWaitingListException::class.java) {
            waitingList.addUser(user1, UserState.ENTERED)
        }
        assertEquals("Waiting list has multiple of the same user", 1, waitingList.getSize())
    }

    @Test
    fun `addUser should not add when capacity is reached`() {
        val capacity = 2
        waitingList = WaitingList(capacity = capacity)

        waitingList.addUser(user1, UserState.ENTERED)
        waitingList.addUser(user2, UserState.ENTERED)

        assertTrue("The list should be full before attempting the third addition", waitingList.isFull())
        assertEquals("The capacity does not equal the number of entrants", capacity, waitingList.getSize())

        assertThrows(EventFullException::class.java) {
            waitingList.addUser(user3, UserState.ENTERED)
        }

        assertEquals(
            "The waiting list size should remain at the defined capacity ($capacity)",
            capacity,
            waitingList.getSize()
        )
        assertFalse("User3 should not have been added because capacity was reached", waitingList.hasUser(user3))
    }
}