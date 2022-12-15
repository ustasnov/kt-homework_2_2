package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NotesServiceTest {

    @Before
    fun clearBeforeTest() {
        NotesService.clear()
    }

    @Test
    fun add() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        assertTrue(noteId != 0L)
    }

    @Test
    fun edit() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        assertTrue(NotesService.edit(noteId, "Заметка 1 после изменения", "Текст заметки 1 после редактирования", 1, 1))
    }

    @Test(expected = NotFoundException::class)
    fun editShouldThrow() {
        NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.edit(4, "Заметка 4 после изменения", "Текст заметки 4 после редактирования", 1, 1)
    }

    @Test
    fun delete() {
        NotesService.add("Заметка 1", "Текст заметки 1")
        assertTrue(NotesService.delete(1))
    }

    @Test(expected = NotFoundException::class)
    fun deleteShouldThrow() {
        NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.delete(2)
    }

    @Test
    fun getById() {
        NotesService.add("Заметка 1", "Текст заметки 1")
        assertTrue(NotesService.getById(1).id == 1L)
    }

    @Test(expected = NotFoundException::class)
    fun getByIdShouldThrow() {
        NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.getById(2)
    }

    @Test
    fun getNotes() {
        NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.add("Заметка 2", "Текст заметки 2")
        val notes = NotesService.getNotes()
        assertTrue(notes.size == 2)
    }

    @Test
    fun printNotes() {
        var noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.createComment(noteId, 1, 3, "Комментарий 2 к заметке 1")

        noteId = NotesService.add("Заметка 2", "Текст заметки 2")
        NotesService.createComment(noteId, 2, 1, "Комментарий 1 к заметке 2")
        NotesService.createComment(noteId, 2, 1, "Комментарий 2 к заметке 2")

        NotesService.printNotes()
        assertTrue(true)
    }

    @Test
    fun clear() {
        assertTrue(true)
    }

    @Test
    fun createComment() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        val commentId = NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        assertTrue(commentId != 0L)
    }

    @Test(expected = NotFoundException::class)
    fun createCommentShouldThrow() {
        NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.createComment(2, 1, 2, "Комментарий 1 к заметке 2")
    }

    @Test
    fun editComment() {
        val newMessage = "Новый Комментарий 1 к заметке 1)"
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        val commentId = NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        assertTrue(NotesService.editComment(noteId, commentId, newMessage))
    }

    @Test(expected = NotFoundException::class)
    fun editCommentShouldThrow() {
        val newMessage = "Новый Комментарий 1 к заметке 1)"
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        val commentId = NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.editComment(2, commentId, newMessage)
    }

    @Test(expected = NotFoundException::class)
    fun editCommentShouldThrow_1() {
        val newMessage = "Новый Комментарий 1 к заметке 1)"
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.editComment(noteId, 2, newMessage)
    }

    @Test
    fun deleteComment() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        val commentId = NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        assertTrue(NotesService.deleteComment(noteId, commentId))
    }

    @Test(expected = NotFoundException::class)
    fun deleteCommentShouldThrow() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        val commentId = NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.deleteComment(2, commentId)
    }

    @Test(expected = NotFoundException::class)
    fun deleteCommentShouldThrow_1() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.deleteComment(noteId, 2)
    }

    @Test
    fun getComments() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.createComment(noteId, 1, 3, "Комментарий 2 к заметке 1")
        val comments = NotesService.getComments(noteId)
        assertTrue(comments.size == 2)
    }

    @Test(expected = NotFoundException::class)
    fun getCommentsShouldThrow() {
        NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.createComment(2, 1, 2, "Комментарий 1 к заметке 2")
    }

    @Test
    fun printCommentsEmptyCommentsMap() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.printComments(noteId)
    }

    @Test(expected = NotFoundException::class)
    fun printComments() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.createComment(noteId, 1, 3, "Комментарий 2 к заметке 1")
        NotesService.printComments(2)
    }

    @Test
    fun restoreComment() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        val commentId = NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.deleteComment(noteId, commentId)
        assertTrue(NotesService.restoreComment(noteId, commentId))
    }

    @Test(expected = NotFoundException::class)
    fun restoreCommentShouldThrow() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        val commentId = NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.deleteComment(noteId, commentId)
        NotesService.restoreComment(2, commentId)
    }

    @Test(expected = NotFoundException::class)
    fun restoreCommentShouldThrow_1() {
        val noteId = NotesService.add("Заметка 1", "Текст заметки 1")
        val commentId = NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
        NotesService.deleteComment(noteId, commentId)
        NotesService.restoreComment(noteId, 2)
    }
}