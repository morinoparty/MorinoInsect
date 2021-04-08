package com.github.morinoparty.morinoinsect.announcement

/**
 * Created by elsiff on 2019-01-15.
 */
class AnnouncementLoader {
    fun loadFrom(catchAnnounce: Int): BaseAnnouncement {
        return when (catchAnnounce) {
            -2 -> BaseAnnouncement.ofEmpty()
            -1 -> BaseAnnouncement.ofServerBroadcast()
            0 -> BaseAnnouncement.ofCatcherOnly()
            else -> BaseAnnouncement.ofRanged(catchAnnounce.toDouble())
        }
    }
}
