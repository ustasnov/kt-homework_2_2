package ru.netology

import java.time.LocalDateTime

data class PhotoCopy(
    val url: String,
    val width: Int = 0,
    val height: Int = 0,
    val type: String = "x"
)

data class Photo(
    val id: Int = 0,
    val ownerId: Int = 0,
    val userId: Int = 0,
    val albumId: Int = 0,
    val text: String = "",
    val date: LocalDateTime,
    var sizes: Array<PhotoCopy> = emptyArray(),
    val width: Int = 0,
    val height: Int = 0
)

data class Audio(
    val id: Int = 0,
    val ownerId: Int = 0,
    val artist: String = "",
    val title: String = "",
    val duration: Int = 0,
    val url: String = "",
    val lyricsId: Int = 0,
    val albumId: Int = 0,
    val genreId: Int = 0,
    val date: LocalDateTime,
    val noSearch: Int = 0,
    val isHq:Int = 0
)

data class Cover(
   val height: Int = 0,
   val url: String = "",
   val width: Int = 0,
   val withPadding: Int = 1
)

data class Frame(
    val url: String = "",
    val width: Int = 0,
    val height: Int = 0
)

data class VideoReposts(
    val count: Int = 0,
    val wallCount: Int = 0,
    val mailCount: Int = 0,
    val userReposted: Int = 0
)

data class Video(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val description: String = "",
    val duration: Int = 0,
    var image: Array<Cover> = emptyArray(),
    var firstFrame: Array<Frame> = emptyArray(),
    val date: LocalDateTime,
    val addingDate: LocalDateTime,
    val views: Int = 0,
    val localViews: Int = 0,
    val comments: Int = 0,
    val player: String = "",
    val platform: String = "",
    val canAdd: Boolean = true,
    val isPrivate: Int = 1,
    val isFavorite: Boolean = false,
    val canComment: Boolean = true,
    val canEdit: Boolean = true,
    val canLike: Boolean = true,
    val canRepost: Boolean = true,
    val canSubscribe: Boolean = true,
    val canAddToFaves: Boolean = true,
    val canAttachLink: Boolean = true,
    val width: Int = 0,
    val height: Int = 0,
    val userId: Int = 0,
    val converting: Boolean = false,
    val added: Boolean = false,
    val isSubscribed: Boolean = false,
    val repeat: Int = 1,
    val type: String = "video",
    val balance: Int = 0,
    val liveStatus: String = "",
    val live: Int = 1,
    val upcoming: Int = 1,
    val spectrators: Int = 0,
    var likes: Likes = Likes(),
    var reposts: VideoReposts? = null
)

data class Preview(
    val sizes: Array<PhotoCopy> = emptyArray()
)

data class Graffiti(
    val src: String = "",
    val width: Int = 0,
    val height: Int = 0
)

data class AudioMessage(
    val duration: Int = 0,
    var WaveForm: Array<Int> = emptyArray(),
    val linkOgg: String,
    val linkMp3: String
)

data class Doc(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val size: Int = 0,
    val ext: String = "",
    val url: String = "",
    val date: LocalDateTime,
    val type: Int = 1,
    var photo: Preview? = null,
    var graffiti: Graffiti? = null,
    var audioMessage: AudioMessage? = null
)

data class Sticker(
    val productId: Int = 0,
    val stickerId: Int = 0,
    var images: Array<Frame> = emptyArray(),
    var imagesWithBackground: Array<Frame> = emptyArray(),
    val animationUrl: String = "",
    val isAllowed: Boolean = true
)

interface Attachment {
    val type: String
}

data class PhotoAttachment(
    override val type: String = "photo",
    val photo: Photo? = null
): Attachment

data class AudioAttachment(
    override val type: String = "audio",
    val audio: Audio? = null
): Attachment

data class VideoAttachment(
    override val type: String = "video",
    val video: Video? = null
): Attachment

data class DocAttachment(
    override val type: String = "doc",
    val doc: Doc? = null
): Attachment

data class StickerAttachment(
    override val type: String = "sticker",
    val sticker: Sticker? = null
): Attachment
