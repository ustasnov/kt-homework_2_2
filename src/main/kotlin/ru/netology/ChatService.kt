package ru.netology

import java.time.LocalDateTime

data class Message(
    val id: Long = 0,
    val message: String,
    val date: LocalDateTime,
    val incoming: Boolean = false,
    var unread: Boolean = true
)

data class Chat(
    val id: Long = 0,
    var messages: GenericService<Message> = GenericService()
)

interface LastMessageResult;

data class LastMessage(val message: Message): LastMessageResult
data class SystemMessage(val text: String): LastMessageResult

object ChatService {
    private var chats = GenericService<Chat>()

    fun add(): Chat? {
        val id = chats.add(Chat(chats.maxIndex + 1))
        return chats.getById(id)
    }

    fun delete(chatId: Long): Boolean {
        return if (chats.delete(chatId)) {
            true
        } else {
            throw NotFoundException("Чат с id == $chatId не найден!")
        }
    }

    fun getById(chatId: Long): Chat {
        return chats.getById(chatId) ?: throw NotFoundException("Чат с id == $chatId не найден!")
    }

    fun getChats(): MutableMap<Long, Chat> {
        return chats.read()
    }

    fun printChats(setUnread: Boolean = true) {
        println("\nСписок чатов:\n---------------")
        chats.read().forEach { chat -> println("$chat.value")
            printMessages(chat.value.id, chat.value.messages.size(), setUnread) }

    }

    fun clear() {
        chats.clear()
    }

    fun createMessage(chatId: Long, message: String, incoming: Boolean): Long {
        var chat: Chat? = chats.getById(chatId) ?: add()
        if (chat != null) {
            return chat.messages.add(Message(chat.messages.maxIndex + 1, message, LocalDateTime.now(), incoming))
        }
        return 0
    }

    fun editMessage(chatId: Long, messageId: Long, message: String, unread: Boolean): Boolean {
        var chat: Chat? = chats.getById(chatId) ?: throw NotFoundException("Чат с id == $chatId не найден!")
        if (chat != null) {
            var mes: Message? = chat.messages.getById(messageId)
                ?: throw NotFoundException("Сообщение с id == $messageId в чате с id == $chatId не найдено!")
            if (mes != null) {
                return chat.messages.update(messageId, Message(messageId, message, mes.date, mes.incoming, unread)
                )
            }
        }
        return false
    }

    fun deleteMessage(chatId: Long, messageId: Long) {
        val chat = chats.getById(chatId) ?: throw NotFoundException("Чат с id == $chatId не найден!")
        val mes = chat.messages.getById(messageId) ?: throw NotFoundException("Сообщение с id == $messageId в чате с id == $chatId не найдено!")
        chat.messages.delete(mes.id)
        if (chat.messages.size() == 0) {
            chats.delete(chatId)
        }
    }

    fun getMessages(chatId: Long, count: Int, setUnread: Boolean = true): List<Message> {
        val chat = chats.getById(chatId) ?: throw NotFoundException("Чат с id == $chatId не найден!")
        val result = chat.messages.readToList().takeLast(count)
        if (setUnread) {
            result.onEach { it.unread = false }
        }
        return result
    }

    fun printMessages(chatId: Long, count: Int, setUnread: Boolean = true) {
        val messages = getMessages(chatId, count, setUnread)
        if (messages.isEmpty()) {
            println("  В чате с id == $chatId нет сообщений")
            return
        }
        println("  Сообщения в чате с id == $chatId:")
        for (message in messages) {
            println("  $message")
        }
    }

    fun getUnreadChatsCount() = getChats().values.count { chat -> getMessages(chat.id, chat.messages.size(), false).any { it.unread } }

    fun getLastMessages(): List<LastMessageResult> {
        return chats.read().values.map { chat ->
            if (chat.messages.size() == 0) SystemMessage("Нет сообщений") else LastMessage(chat.messages.readToList().last()) }
    }
}