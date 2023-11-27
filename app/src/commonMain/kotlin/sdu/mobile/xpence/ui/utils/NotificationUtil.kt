package sdu.mobile.xpence.ui.utils

expect object NotificationUtil {
    fun init(context: Any)
    fun createNotificationChannel(
        channelId: String,
        channelName: String,
        channelDescription: String
    )

    fun sendNotification(
        channelId: String,
        title: String,
        content: String)

}