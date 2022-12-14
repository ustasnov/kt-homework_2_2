package ru.netology

import java.lang.RuntimeException

data class Note(
    val id: Int = 0,
    val title: String = "",
    val text: String = "",
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
    val comments: ArrayList<Comment> = ArrayList<Comment>()
)

class NotFoundException(
    override val message: String?
): RuntimeException()

object NotesService {
    private val notes = ArrayList<Note>()

    fun add(title: String, text: String, privacy: Int, commentPrivacy: Int): Int {
        notes += Note(notes.lastIndex + 2, title, text, privacy, commentPrivacy)
        return notes.last().id
    }

    fun edit(noteId: Int, title: String, text: String, privacy: Int, commentPrivacy: Int): Int {
        for ((index, curNote) in notes.withIndex()) {
            if (curNote.id == noteId) {
                notes[index] = curNote.copy(
                    id = noteId,
                    title = title,
                    text = text,
                    privacy = privacy,
                    commentPrivacy = commentPrivacy
                )
                return 1
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

    fun getNotes(): ArrayList<Note> {
        val result = ArrayList<Note>()
        notes.toCollection(result)
        return result
    }


}