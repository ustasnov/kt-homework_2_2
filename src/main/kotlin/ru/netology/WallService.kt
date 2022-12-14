package ru.netology

import java.lang.RuntimeException
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

data class Donut(
    val isDon: Boolean = false,
    val placeholder: String = ""
)

data class CommentThread(
    val count: Int = 0,
    val items: Array<Comment> = emptyArray(),
    val canPost: Boolean = true,
    val showReplyButton: Boolean = true,
    val groupsCanPost: Boolean = true
)

data class Comment(
    val id: Int = 0,
    val postId: Int = 0,
    val fromId: Int = 0,
    val date: LocalDateTime,
    val text: String = "",
    val donut: Donut? = null,
    val replyToUser: Int = 0,
    val replyToComment: Int = 0,
    var attachments: Array<Attachment> = emptyArray(),
    var parentStack: Array<Int> = emptyArray(),
    val thread: CommentThread =  CommentThread()
)

class PostNotFoundException(
    override val message: String?
): RuntimeException()

object WallService {
    var posts = emptyArray<Post>()
    var comments = emptyArray<Comment>()

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

    fun createComment(postId: Int, comment: Comment): Comment {
        for ((index, curPost) in posts.withIndex()) {
            if (curPost.id == postId) {
                comments += comment
                return comment
            }
        }
        throw PostNotFoundException("Пост с id = $postId не найден!")
    }

    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
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

fun printComments(title: String) {
    println(title)
    for ((index, curComment) in WallService.comments.withIndex()) {
        println("  ${index + 1}: ${curComment}")
    }
}