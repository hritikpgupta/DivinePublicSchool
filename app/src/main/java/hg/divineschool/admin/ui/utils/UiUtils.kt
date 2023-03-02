package hg.divineschool.admin.ui.utils

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import java.io.Serializable
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun <A : Activity> Context.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes message: Int) {
    Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
}

fun String?.toStringOrEmpty(): String {
    return this ?: ""
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun Boolean.getTextAlignment() : TextAlign{
    return if (this){
        TextAlign.Start
    }else{
        TextAlign.Center
    }
}

fun Boolean.getActivatedColor(color : Color) : Color{
    return if (this){
        return color
    }else {
        return Color.LightGray
    }
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Bundle.customGetSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getSerializable(key, T::class.java)
    else getSerializable(key) as? T
}

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        activity.requestedOrientation = orientation
        onDispose {

        }
    }
}

fun Long.convertIdToPath(): String {
    return if (this.toInt() == 0) {
        "classPlayGroup"
    } else if (this.toInt() == 1) {
        "classLowerNursery"
    } else if (this.toInt() == 2) {
        "classUpperNursery"
    } else if (this.toInt() == 3) {
        "classOne"
    } else if (this.toInt() == 4) {
        "classTwo"
    } else if (this.toInt() == 5) {
        "classThree"
    } else if (this.toInt() == 6) {
        "classFour"
    } else if (this.toInt() == 7) {
        "classFive"
    } else if (this.toInt() == 8) {
        "classSix"
    } else if (this.toInt() == 9) {
        "classSeven"
    } else if (this.toInt() == 10) {
        "classEight"
    } else {
        ""
    }
}


fun String.convertIdToPath(): String {
    return if (this.toInt() == 0) {
        "classPlayGroup"
    } else if (this.toInt() == 1) {
        "classLowerNursery"
    } else if (this.toInt() == 2) {
        "classUpperNursery"
    } else if (this.toInt() == 3) {
        "classOne"
    } else if (this.toInt() == 4) {
        "classTwo"
    } else if (this.toInt() == 5) {
        "classThree"
    } else if (this.toInt() == 6) {
        "classFour"
    } else if (this.toInt() == 7) {
        "classFive"
    } else if (this.toInt() == 8) {
        "classSix"
    } else if (this.toInt() == 9) {
        "classSeven"
    } else if (this.toInt() == 10) {
        "classEight"
    } else {
        ""
    }
}
