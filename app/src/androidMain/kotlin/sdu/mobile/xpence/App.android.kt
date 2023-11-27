package sdu.mobile.xpence

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import sdu.mobile.xpence.ui.utils.NotificationUtil


class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        NotificationUtil.init(this)
        NotificationUtil.createNotificationChannel("0","test","this is a test")
        NotificationUtil.createNotificationChannel("1","Group","These notifications are for getting news on groups")
        NotificationUtil.createNotificationChannel("2","Account","These notifications are for getting information of log ins and threats to your account")
        NotificationUtil.createNotificationChannel("3","Ads","These notifications ads")
        NotificationUtil.createNotificationChannel("4","Other","miscellaneous notifications ")

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