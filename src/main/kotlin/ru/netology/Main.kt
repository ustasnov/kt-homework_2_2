package ru.netology

import java.time.LocalDateTime

data class Likes(
    val userLikes: Boolean = true,
    val canLike: Boolean = true,
    val canPublish: Boolean = true,
    var count: Int = 0
)

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = true
)

data class Views(
    val count: Int = 0
)

data class PostSource(
    val type: String = "vk",
    val platform: String = "android",
    val data: String = "profile_activity",
    val url: String = ""
)

data class Geo(
    val type: String = "",
    val coordinates: String = "",
    val place: String = ""
)

data class Repost(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val date: LocalDateTime,
    val original: Post?,
    val likes: Int
)

data class Post(
    val id: Int = 0,
    val ownerId: Int = 0,
    val fromId: Int = 0,
    val createdBy: Int = 0,
    val text: String = "",
    val date: LocalDateTime,
    val replyOwnerId: Int = 0,
    val replyPostId: Int = 0,
    val friendsOnly: Boolean = false,
    var comments: Comments? = null,
    val copyright: String = "",
    var likes: Likes = Likes(),
    var reposts: Reposts? = null,
    val views: Views = Views(),
    val postType: String = "post",
    var postSource: PostSource = PostSource(),
    var geo: Geo? = null,
    val signerId: Int = 0,
    var copyHistory: Array<Repost> = emptyArray(),
    val canPin: Boolean = true,
    val canDelete: Boolean = true,
    val canEdit: Boolean = true,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val postponedId: Int = 0,
    var attachments: Array<Attachment> = emptyArray()
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
                posts[index] = post.copy(date = curPost.date, attachments = curPost.attachments)
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
    }
}

fun printPosts(title: String) {
    println(title)
    for ((_, curPost) in WallService.posts.withIndex()) {
        println(curPost)
        println("  Вложения:\n  ---------")
        for ((index, attachment) in curPost.attachments.withIndex()) {
            println("  ${index + 1}: ${attachment}")
        }
        println()
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
}