package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.time.LocalDateTime

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {
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

        post = WallService.add(post)
        assertTrue(post.id != 0)
    }

    @Test
    fun update() {
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
        WallService.add(post)

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

        assertTrue(WallService.update(post))
    }

    @Test
    fun update_false() {
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
        WallService.add(post)

        post = Post(
            id = 3,
            ownerId = 3,
            fromId = 4,
            createdBy = 3,
            text = "`Third post after update",
            date = LocalDateTime.now(),
            friendsOnly = true,
            replyOwnerId = 5,
            replyPostId = 5,
            likes = Likes(count = 101)
        )

        assertFalse(WallService.update(post))
    }

    @Test
    fun createComment() {
        val post = Post(
            ownerId = 1,
            fromId = 1,
            createdBy = 1,
            text = "First post",
            date = LocalDateTime.now(),
            friendsOnly = false,
            replyOwnerId = 1,
            replyPostId = 1
        )

        WallService.add(post)

        val comment = Comment(1, 1, 1, LocalDateTime.now(), "Комментарий к посту 1")
        assertTrue(comment === WallService.createComment(1, comment))
    }

    @Test(expected = PostNotFoundException::class)
    fun createCommentShouldThrow() {
        val post = Post(
            ownerId = 1,
            fromId = 1,
            createdBy = 1,
            text = "First post",
            date = LocalDateTime.now(),
            friendsOnly = false,
            replyOwnerId = 1,
            replyPostId = 1
        )

        WallService.add(post)

        val comment = Comment(1, 3, 1, LocalDateTime.now(), "Комментарий к посту 3")
        WallService.createComment(3, comment)
    }
}