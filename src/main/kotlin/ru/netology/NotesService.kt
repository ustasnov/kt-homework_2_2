package ru.netology

import java.lang.RuntimeException
import java.time.LocalDateTime

interface GenericServiceInterface<T> {
    fun add(element: T): Long
    fun delete(id: Long): Boolean
    fun update(id: Long, element: T): Boolean
    fun read(): MutableMap<Long, T>
    fun getById(id: Long): T?
    fun restore(id: Long): Boolean
    fun clear()
}

open class GenericService<T : Any> : GenericServiceInterface<T> {
    private var elements: MutableMap<Long, T> = mutableMapOf()
    private var deletedElements: MutableMap<Long, T> = mutableMapOf()
    var maxIndex: Long = 0

    override fun add(element: T): Long {
        elements[++maxIndex] = element
        return maxIndex
    }

    override fun delete(id: Long): Boolean {
        return if (elements.containsKey(id)) {
            deletedElements[id] = elements[id]!!
            elements.remove(id)
            true
        } else {
            false
        }
    }

    override fun read(): MutableMap<Long, T> {
        val result: MutableMap<Long, T> = mutableMapOf()
        result.putAll(elements)
        return result
    }

    fun readToList(): MutableList<T> {
        val result: MutableList<T> = mutableListOf()
        result.addAll(elements.values)
        return result
    }

    fun size(): Int {
        return elements.size
    }

    override fun getById(id: Long): T? {
        return elements[id]
    }

    override fun restore(id: Long): Boolean {
        return if (deletedElements.containsKey(id)) {
            elements[id] = deletedElements[id]!!
            deletedElements.remove(id)
            true
        } else {
            false
        }
    }

    override fun clear() {
        deletedElements.clear()
        elements.clear()
        maxIndex = 0
    }

    override fun update(id: Long, element: T): Boolean {
        return if (elements.containsKey(id)) {
            elements.replace(id, element)
            true
        } else {
            false
        }
    }
}

data class NoteComment(
    val id: Long = 0,
    val uId: Int = 0,
    val nId: Long = 0,
    val oId: Int = 0,
    val date: LocalDateTime,
    val message: String,
    val replyTo: Int = 0,
    val deleted: Boolean = false
)

data class Note(
    val id: Long = 0,
    val title: String = "",
    val text: String = "",
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    var comments: GenericService<NoteComment> = GenericService()
)

class NotFoundException(
    override val message: String?
) : RuntimeException()

object NotesService {
    private var notes = GenericService<Note>()

    fun add(title: String, text: String, privacy: Int = 0, commentPrivacy: Int = 0): Long {
        return notes.add(Note(notes.maxIndex + 1, title, text, privacy, commentPrivacy))
    }

    fun edit(noteId: Long, title: String, text: String, privacy: Int, commentPrivacy: Int): Boolean {
        return if (notes.update(noteId, Note(noteId, title, text, privacy, commentPrivacy))) {
            true
        } else {
            throw NotFoundException("?????????????? ?? id == $noteId ???? ??????????????!")
        }
    }

    fun delete(noteId: Long): Boolean {
        return if (notes.delete(noteId)) {
            true
        } else {
            throw NotFoundException("?????????????? ?? id == $noteId ???? ??????????????!")
        }
    }

    fun getById(noteId: Long): Note {
        return notes.getById(noteId) ?: throw NotFoundException("?????????????? ?? id == $noteId ???? ??????????????!")
    }

    fun getNotes(): MutableMap<Long, Note> {
        return notes.read()
    }

    fun printNotes() {
        println("\n???????????? ??????????????:\n---------------")
        val notesMap = notes.read()
        for (note in notesMap) {
            println("${note.value}")
            printComments(note.value.id)
        }
    }

    fun clear() {
        notes.clear()
    }

    fun createComment(noteId: Long, ownerId: Int, replyTo: Int, message: String): Long {
        var note: Note? = notes.getById(noteId) ?: throw NotFoundException("?????????????? ?? id == $noteId ???? ??????????????!")
        if (note != null) {
            return note.comments.add(
                NoteComment(
                    note.comments.maxIndex + 1,
                    1,
                    noteId,
                    ownerId,
                    LocalDateTime.now(),
                    message,
                    replyTo
                )
            )
        }
        return 0
    }

    fun editComment(noteId: Long, commentId: Long, message: String): Boolean {
        var note: Note? = notes.getById(noteId) ?: throw NotFoundException("?????????????? ?? id == $noteId ???? ??????????????!")
        if (note != null) {
            var comment: NoteComment? = note.comments.getById(commentId)
                ?: throw NotFoundException("?????????????????????? ?? id == $commentId ?? ?????????????? ?? id == $noteId ???? ????????????!")
            if (comment != null) {
                return note.comments.update(
                    commentId,
                    NoteComment(commentId, comment.uId, noteId, comment.oId, comment.date, message, comment.replyTo)
                )
            }
        }
        return false
    }

    fun deleteComment(noteId: Long, commentId: Long): Boolean {
        var note: Note? = notes.getById(noteId) ?: throw NotFoundException("?????????????? ?? id == $noteId ???? ??????????????!")
        if (note != null) {
            val comment: NoteComment? = note.comments.getById(commentId)
                ?: throw NotFoundException("?????????????????????? ?? id == $commentId ?? ?????????????? ?? id == $noteId ???? ????????????!")
            if (comment != null) {
                return note.comments.delete(comment.id)
            }
        }
        return false
    }

    fun getComments(noteId: Long): MutableMap<Long, NoteComment> {
        var note: Note? = notes.getById(noteId) ?: throw NotFoundException("?????????????? ?? id == $noteId ???? ??????????????!")
        if (note != null) {
            return note.comments.read()
        }
        return mutableMapOf()
    }

    fun printComments(noteId: Long) {
        val comments = getComments(noteId)
        if (comments.size == 0) {
            println("  ?? ?????????????? ?? id == $noteId ?????? ????????????????????????")
            return
        }
        println("  ?????????????????????? ?? ?????????????? ?? id == $noteId:")
        for (comment in comments) {
            println("  ${comment.value}")
        }
    }

    fun restoreComment(noteId: Long, commentId: Long): Boolean {
        var note: Note? = notes.getById(noteId) ?: throw NotFoundException("?????????????? ?? id == $noteId ???? ??????????????!")
        if (note != null) {
            if (!note.comments.restore(commentId)) {
                throw NotFoundException("?????????????????????? ?? id = $commentId ?? ?????????????? ?? id = $noteId ???? ????????????!")
            }
            return true
        }
        return false
    }
}