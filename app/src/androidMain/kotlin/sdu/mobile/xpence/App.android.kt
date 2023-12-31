package sdu.mobile.xpence

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.Constants
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import sdu.mobile.xpence.ui.utils.FirebaseUtil
import sdu.mobile.xpence.ui.utils.NotificationUtil
import sdu.mobile.xpence.ui.utils.authenticationState
import sdu.mobile.xpence.ui.utils.getCurrentUser

var isRun = false

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    fun saveTokenToDatabase() {
        try {
            if (!authenticationState.isLoggedIn()) {
                FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                    println(token)
                    if (token != null) {
                        val userTokenMap = hashMapOf("token" to token)
                        FirebaseUtil.instance.collection("users")
                            .document(token) // replace with actual user ID
                            .set(userTokenMap)
                            .addOnSuccessListener {
                                Log.d(Constants.MessageNotificationKeys.TAG, "Token successfully written!")
                            }
                            .addOnFailureListener { e ->
                                Log.w(Constants.MessageNotificationKeys.TAG, "Error writing token", e)
                            }
                    }
                }
            }
        } catch (error: Exception) {
        }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        FirebaseApp.initializeApp(this)
        NotificationUtil.init(this)
        NotificationUtil.createNotificationChannel("0", "test", "this is a test")
        NotificationUtil.createNotificationChannel("1", "Group", "These notifications are for getting news on groups")
        NotificationUtil.createNotificationChannel(
            "2",
            "Account",
            "These notifications are for getting information of log ins and threats to your account"
        )
        NotificationUtil.createNotificationChannel("3", "Ads", "These notifications ads")
        NotificationUtil.createNotificationChannel("4", "Other", "miscellaneous notifications ")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.d(TAG, "FCM Token: $token")
        })

        //saveTokenToDatabase() // Breaks production

    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var hasNotificationPermission by remember {
                mutableStateOf(checkNotificationPermission(this))
            }

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                    hasNotificationPermission = isGranted
                }
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !hasNotificationPermission) {
                LaunchedEffect(Unit) {
                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            App()

            NotificationUtil.sendNotification("1", "WELCOME", "HELLO AND WELCOME TO XPENCE")
        }
    }
}

private fun checkNotificationPermission(context: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    } else {
        true // No runtime permission required for versions below Android 13
    }
}