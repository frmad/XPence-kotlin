package sdu.mobile.xpence.ui.utils

import android.util.Log
import androidx.compose.runtime.getValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseUtil : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {

    }

    companion object {
        val instance: FirebaseFirestore by lazy {
            FirebaseFirestore.getInstance()
        }
    }

}