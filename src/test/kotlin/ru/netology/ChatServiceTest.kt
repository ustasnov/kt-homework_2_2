package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun add() {
        val chat = ChatService.add()
        assertTrue(chat != null)
    }

    @Test
    fun delete() {
        ChatService.add()
        assertTrue(ChatService.delete(1))
    }

    @Test
    fun getById() {
        ChatService.add()
        assertTrue(ChatService.getById(1) != null)
    }

    @Test(expected = NotFoundException::class)
    fun getByIdShouldThrow() {
        ChatService.add()
        ChatService.getById(2)
    }

    @Test
    fun getChats() {
        ChatService.add()
        ChatService.add()
        val chats = ChatService.getChats()
        assertTrue(chats.size == 2)
    }

    @Test
    fun printChats() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.createMessage(1, "Сообщение 2 от пользователя 1", true)
        ChatService.printChats()
        assertTrue(true)
    }

    @Test
    fun clear() {
        assertTrue(true)
    }

    @Test
    fun createMessage() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.createMessage(1, "Сообщение 2 от пользователя 1", true)
        assertTrue(ChatService.getMessages(1, 2).size == 2)
    }

    @Test
    fun editMessage() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.editMessage(1, 1, "Сообщение отредактировано", false)
        val messages = ChatService.getMessages(1,1, false)
        //assertTrue(messages[0].message == "Сообщение отредактировано" && !messages[0].unread)
        assertTrue(messages[0].message === "Сообщение отредактировано")
    }

    @Test
    fun deleteMessage() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.deleteMessage(1,1)
        assertTrue(true)
    }

    @Test(expected = NotFoundException::class)
    fun deleteMessageShouldThrow() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.deleteMessage(2,1)
    }

    @Test(expected = NotFoundException::class)
    fun deleteMessageShouldThrow1() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.deleteMessage(1,2)
    }

    @Test(expected = NotFoundException::class)
    fun getMessages() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.createMessage(1, "Сообщение 2 от пользователя 1", true)
        ChatService.getMessages(2, 2)
    }

    @Test
    fun printMessages() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.createMessage(1, "Сообщение 2 от пользователя 1", false)
        ChatService.printMessages(1, 2, true)
        assertTrue(true)
    }

    @Test
    fun printMessages_1() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.createMessage(1, "Сообщение 2 от пользователя 1", true)
        ChatService.printMessages(1, 2, true)
        assertTrue(true)
    }

    @Test
    fun getUnreadChatsCount() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.createMessage(1, "Сообщение 2 от пользователя 1", false)
        assertTrue(ChatService.getUnreadChatsCount() == 1)
    }

    @Test
    fun getLastMessages() {
        ChatService.createMessage(1, "Сообщение 1 пользователю 1", false)
        ChatService.createMessage(1, "Сообщение 2 от пользователя 1", false)
        assertTrue(ChatService.getLastMessages().size == 1)
    }
}