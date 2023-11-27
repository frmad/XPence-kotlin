import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import sdu.mobile.xpence.App
import org.jetbrains.skiko.wasm.onWasmReady
import sdu.mobile.xpence.ui.utils.NotificationHelper

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("XPence") {
            App()
        }
    }
}
