package ru.netology

import java.time.LocalDateTime

data class Likes(
    val userLikes: Boolean = true,
    val canLike: Boolean = true,
    val canPublish: Boolean = true,
    var count: Int = 0
)

data class Post(
    val id: Int = 0,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int,
    val text: String,
    val date: LocalDateTime,
    val friendsOnly: Boolean = false,
    val replyOwnerId: Int,
    val replyPostId: Int,
    var likes: Likes = Likes()
)

object WallService {
    var posts = emptyArray<Post>()

    fun add(post: Post): Post {
        posts += post.copy(id = posts.lastIndex + 2)
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, curPost) in posts.withIndex()) {
            if (curPost.id == post.id) {
                posts[index] = post.copy(date = curPost.date)
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
    }
}

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
    post = WallService.add(post)

    println("Массив постов:\n--------------")
    for ((_, curPost) in WallService.posts.withIndex()) {
        println(curPost)
    }

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
        println()
        println("Массив постов после изменения поста с id == 1:\n-----------------------------------------------")
        for ((_, curPost) in WallService.posts.withIndex()) {
            println(curPost)
        }
    }
}