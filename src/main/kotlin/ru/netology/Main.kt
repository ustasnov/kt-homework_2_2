package ru.netology

import java.time.LocalDateTime

fun main() {
    var post = Post(
        ownerId = 1,
        fromId = 1,
        createdBy = 1,
        text = "First post",
        date = LocalDateTime.now(),
        friendsOnly = false,
        replyOwnerId = 1,
        replyPostId = 1
    )

    post.attachments += PhotoAttachment(photo = Photo(id = 1, text = "Фото 1", date = LocalDateTime.now()))
    post.attachments += AudioAttachment(audio = Audio(id = 2, title = "Аудиозапись 1", date = LocalDateTime.now()))
    post.attachments += VideoAttachment(video = Video(id = 3, description = "Видео 1", date = LocalDateTime.now(), addingDate = LocalDateTime.now()))
    post.attachments += DocAttachment(doc = Doc(id = 4, title = "Документ 1", date = LocalDateTime.now()))
    post.attachments += StickerAttachment(sticker = Sticker(stickerId = 5))

    /*val service = WallService*/
    WallService.add(post)

    post = Post(
        ownerId = 2,
        fromId = 2,
        createdBy = 2,
        text = "Second post",
        date = LocalDateTime.now(),
        friendsOnly = false,
        replyOwnerId = 1,
        replyPostId = 1
    )
    post.likes.count = 100
    post.attachments += PhotoAttachment(photo = Photo(id = 1, text = "Фото 2", date = LocalDateTime.now()))
    post.attachments += AudioAttachment(audio = Audio(id = 2, title = "Аудиозапись 2", date = LocalDateTime.now()))
    post.attachments += VideoAttachment(video = Video(id = 3, description = "Видео 2", date = LocalDateTime.now(), addingDate = LocalDateTime.now()))
    post.attachments += DocAttachment(doc = Doc(id = 4, title = "Документ 2", date = LocalDateTime.now()))
    post.attachments += StickerAttachment(sticker = Sticker(stickerId = 5))

    post = WallService.add(post)

    printPosts("Массив постов:\n--------------")

    post = Post(
        id = 2,
        ownerId = 3,
        fromId = 4,
        createdBy = 3,
        text = "Second post after update",
        date = LocalDateTime.now(),
        friendsOnly = true,
        replyOwnerId = 5,
        replyPostId = 5,
        likes = Likes(count = 101)
    )

    if (WallService.update(post)) {
        printPosts("Массив постов после изменения поста с id == 1:\n-----------------------------------------------")
    }

    WallService.createComment(1, Comment(1, 1, 1, LocalDateTime.now(), "Комментарий к посту 1"))
    WallService.createComment(1, Comment(2, 1, 2, LocalDateTime.now(), "Комментарий к посту 1"))
    WallService.createComment(2, Comment(1, 2, 3, LocalDateTime.now(), "Комментарий к посту 2"))
    WallService.createComment(2, Comment(2, 2, 1, LocalDateTime.now(), "Комментарий к посту 2"))

    printComments("Комментарии к постам: \n---------------------")

    println("\nПытаемся добавить комментарий к несуществующему посту c id == 3:")
    try {
        WallService.createComment(3, Comment(1, 3, 1, LocalDateTime.now(), "Комментарий к посту 3"))
    } catch (e: PostNotFoundException) {
        println(e.message)
    }

    println("\nСервис заметок\n--------------")
    var noteId = NotesService.add("Заметка 1", "Текст заметки 1")
    NotesService.createComment(noteId, 1, 2, "Комментарий 1 к заметке 1")
    NotesService.createComment(noteId, 1, 3, "Комментарий 2 к заметке 1")

    noteId = NotesService.add("Заметка 2", "Текст заметки 2")
    NotesService.createComment(noteId, 2, 1, "Комментарий 1 к заметке 2")
    NotesService.createComment(noteId, 2, 1, "Комментарий 2 к заметке 2")

    NotesService.printNotes()

    NotesService.deleteComment(2, 1)
    println("\nУдалили комментарий с id == 1 у заметки с id == 2")

    NotesService.printNotes()

    NotesService.restoreComment(2, 1)
    println("\nВосстановили комментарий с id == 1 у заметки с id == 2")

    NotesService.printNotes()
}