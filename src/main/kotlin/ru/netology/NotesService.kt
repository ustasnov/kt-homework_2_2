package ru.netology

import java.lang.RuntimeException
import java.time.LocalDateTime

data class NoteComment(
    val id: Int = 0,
    val uId: Int = 0,
    val nId: Int = 0,
    val oId: Int = 0,
    val date: LocalDateTime,
    val message: String,
    val replyTo: Int = 0,
    val deleted: Boolean = false
)

data class Note(
    val id: Int = 0,
    val title: String = "",
    val text: String = "",
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    var comments: MutableList<NoteComment> = mutableListOf()
)

class NotFoundException(
    override val message: String?
): RuntimeException()

object NotesService {
    private var notes: MutableList<Note> = mutableListOf()

    fun add(title: String, text: String, privacy: Int = 0, commentPrivacy: Int = 0): Int {
        notes += Note(notes.lastIndex + 2, title, text, privacy, commentPrivacy)
        return notes.last().id
    }

    fun edit(noteId: Int, title: String, text: String, privacy: Int, commentPrivacy: Int): Boolean {
        for ((index, curNote) in notes.withIndex()) {
            if (curNote.id == noteId) {
                notes[index] = curNote.copy(
                    id = noteId,
                    title = title,
                    text = text,
                    privacy = privacy,
                    commentPrivacy = commentPrivacy
                )
                return true
            }
        }
        throw NotFoundException("Заметка с id = $noteId не найдена!")
    }

    fun delete(noteId: Int): Int {
        for ((index, curNote) in notes.withIndex()) {
            if (curNote.id == noteId) {
                notes.removeAt(index)
                return 1
            }
        }
        throw NotFoundException("Заметка с id = $noteId не найдена!")
    }

    fun getById(noteId: Int): Note {
        for ((index, curNote) in notes.withIndex()) {
            if (curNote.id == noteId) {
                return curNote.copy()
            }
        }
        throw NotFoundException("Заметка с id = $noteId не найдена!")
    }

    fun getNotes(): MutableList<Note> {
        val result = ArrayList<Note>()
        notes.toCollection(result)
        return result
    }

    fun printNotes() {
        println("\nСписок заметок:\n---------------")
        for ((index, curNote) in notes.withIndex()) {
            println(curNote)
            printComments(curNote.id)
        }
    }

    fun clear() {
        notes.clear()
    }

    fun createComment(noteId: Int, ownerId: Int, replyTo: Int, message: String): Int {
        for ((index, curNote) in notes.withIndex()) {
            if (curNote.id == noteId) {
                curNote.comments.add(NoteComment(curNote.comments.lastIndex + 2, 1, curNote.id, ownerId, LocalDateTime.now(), message, replyTo))
                return 1
            }
        }
        throw NotFoundException("Заметка с id = $noteId не найдена!")
    }

    fun editComment(noteId: Int, commentId: Int, message: String): Boolean {
        for ((index, curNote) in notes.withIndex()) {
            if (curNote.id == noteId) {
                for ((idx, comment) in curNote.comments.withIndex()) {
                    if (commentId == comment.id) {
                        return if (comment.deleted) {
                            curNote.comments[idx] = comment.copy(message = message)
                            true
                        } else {
                            false;
                        }
                    }
                }
                throw NotFoundException("Комментарий с id = $commentId к заметке с id = $noteId не найден!")
            }
        }
        throw NotFoundException("Заметка с id = $noteId не найдена!")
    }

    fun deleteComment(noteId: Int, commentId: Int): Boolean {
        for ((index, curNote) in notes.withIndex()) {
            if (curNote.id == noteId) {
                for ((idx, comment) in curNote.comments.withIndex()) {
                    if (commentId == comment.id) {
                        return if (comment.deleted) {
                            false
                        } else {
                            curNote.comments[idx] = comment.copy(deleted = true)
                            true
                        }
                    }
                }
                throw NotFoundException("Комментарий с id = $commentId к заметке с id = $noteId не найден!")
            }
        }
        throw NotFoundException("Заметка с id = $noteId не найдена!")
    }

    fun getComments(noteId: Int): MutableList<NoteComment> {
        var result = mutableListOf<NoteComment>()
        for ((index, curNote) in notes.withIndex()) {
            if (curNote.id == noteId) {
                for ((idx, comment) in curNote.comments.withIndex()) {
                    if (!comment.deleted) {
                        result.add(comment)
                    }
                }
                return result
            }
        }
        throw NotFoundException("Заметка с id = $noteId не найдена!")
    }

    fun printComments(noteId: Int) {
        for ((index, curNote) in notes.withIndex()) {
                if (curNote.id == noteId) {
                    val noteComments = getComments(noteId)
                    if (noteComments.size == 0) {
                        println("  У заметки с id == $noteId нет комментариев")
                        return
                    }
                    println("  Комментарии к заметке с id == $noteId:")
                    for ((idx, comment) in noteComments.withIndex()) {
                        if (!comment.deleted) {
                            println("  $comment")
                        }

                    }
                    return
                }
        }
        throw NotFoundException("Заметка с id = $noteId не найдена!")
    }

    fun restoreComment(noteId: Int, commentId: Int): Boolean {
        for ((index, curNote) in notes.withIndex()) {
            if (curNote.id == noteId) {
                for ((idx, comment) in curNote.comments.withIndex()) {
                    if (commentId == comment.id) {
                        return if (comment.deleted) {
                            curNote.comments[idx] = comment.copy(deleted = false)
                            true
                        } else {
                            false
                        }
                    }
                }
                throw NotFoundException("Комментарий с id = $commentId к заметке с id = $noteId не найден!")
            }
        }
        throw NotFoundException("Заметка с id = $noteId не найдена!")
    }
}