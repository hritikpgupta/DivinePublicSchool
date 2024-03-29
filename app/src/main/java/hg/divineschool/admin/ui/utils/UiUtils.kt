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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import hg.divineschool.admin.R
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.MonthFee
import hg.divineschool.admin.data.models.SettingItem
import hg.divineschool.admin.data.models.Supplement
import hg.divineschool.admin.ui.theme.Purple700
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Suppress("DEPRECATION")
inline fun <reified T : Serializable> Bundle.customGetSerializable(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) getSerializable(
        key, T::class.java
    )
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

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    color: Color = Color.White,
    align: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        textAlign = align, color = Color.Black, modifier =
        Modifier
            .border(0.5.dp, Color.Black.copy(0.5f))
            .weight(weight)
            .padding(1.dp)
            .background(color = color)
    )
}

@Composable
fun CircularProgress(color: Color = Purple700) {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(color = color, strokeWidth = 4.dp)
    }
}

val settingsItem = listOf(
    SettingItem(1, "Check Dues", R.drawable.due_date, true),
    SettingItem(2, "Transactions", R.drawable.transaction, true),
    SettingItem(3, "Manage Fees", R.drawable.manage_fees, true),
    SettingItem(4, "Manage Books", R.drawable.manage_books, true),
    SettingItem(5, "Manage Location", R.drawable.manage_navigation, true),
    SettingItem(6, "Pending Dues", R.drawable.pending, true),
    SettingItem(7, "Log Out", R.drawable.logout, true)
)

fun Boolean.decideSettingMenu(): List<SettingItem> {
    if (!this) {
        val copySettingItem = settingsItem
        copySettingItem[2].enabled = false
        copySettingItem[3].enabled = false
        copySettingItem[4].enabled = false
        return copySettingItem
    }
    return settingsItem
}


fun <A : Activity> Context.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.toast(@StringRes message: Int) {
    Toast.makeText(this, getString(message), Toast.LENGTH_LONG).show()
}

fun String?.toStringOrEmpty(): String {
    return this ?: ""
}

fun Date.getFormattedValue(): String {
    val format1 = SimpleDateFormat("EEE, dd MMM yyyy HH:mm")
    val cal = Calendar.getInstance()
    cal.time = this
    return format1.format(cal.time)
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

fun Boolean.getTextAlignment(): TextAlign {
    return if (this) {
        TextAlign.Start
    } else {
        TextAlign.Center
    }
}

fun Boolean.getActivatedColor(color: Color): Color {
    return if (this) {
        return color
    } else {
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

fun Int.convertIdToPath(): String {
    return if (this == 0) {
        "classPlayGroup"
    } else if (this == 1) {
        "classLowerNursery"
    } else if (this == 2) {
        "classUpperNursery"
    } else if (this == 3) {
        "classOne"
    } else if (this == 4) {
        "classTwo"
    } else if (this == 5) {
        "classThree"
    } else if (this == 6) {
        "classFour"
    } else if (this == 7) {
        "classFive"
    } else if (this == 8) {
        "classSix"
    } else if (this == 9) {
        "classSeven"
    } else if (this == 10) {
        "classEight"
    } else {
        ""
    }
}




fun String.convertIdToName(): String {
    return if (this.toInt() == 0) {
        "Play Group"
    } else if (this.toInt() == 1) {
        "Lower Nursery"
    } else if (this.toInt() == 2) {
        "Upper Nursery"
    } else if (this.toInt() == 3) {
        "Class One"
    } else if (this.toInt() == 4) {
        "Class Two"
    } else if (this.toInt() == 5) {
        "Class Three"
    } else if (this.toInt() == 6) {
        "Class Four"
    } else if (this.toInt() == 7) {
        "Class Five"
    } else if (this.toInt() == 8) {
        "Class Six"
    } else if (this.toInt() == 9) {
        "Class Seven"
    } else if (this.toInt() == 10) {
        "Class Eight"
    } else {
        ""
    }
}

fun String.convertClassNameToId(): Int {
    return if (this == "Play Group") {
        0
    } else if (this == "Lower Nursery") {
        1
    } else if (this == "Upper Nursery") {
        2
    } else if (this == "Class One") {
        3
    } else if (this == "Class Two") {
        4
    } else if (this == "Class Three") {
        5
    } else if (this == "Class Four") {
        6
    } else if (this == "Class Five") {
        7
    } else if (this == "Class Six") {
        8
    } else if (this == "Class Seven") {
        9
    } else if (this == "Class Eight") {
        10
    } else {
        0
    }
}


fun List<MonthFee>.getExamFee(): Int {
    var isJan = false
    var isSept = false
    this.forEach { monthFee ->
        if (monthFee.month == "January") {
            isJan = true
        }
        if (monthFee.month == "September") {
            isSept = true
        }
    }
    return if (isJan && isSept) {
        2 * FeeStructure.FEE_STRUCT.examFee
    } else if (isJan || isSept) {
        1 * FeeStructure.FEE_STRUCT.examFee
    } else {
        0
    }
}

fun List<MonthFee>.getAnnualFee(isNew : Boolean): Int {
    if (isNew) {
        //If admission/development fee is charged then annual fee shouldn't be charged for that year.
        return 0
    }
    var isJuly = false
    this.forEach { monthFee ->
        if (monthFee.month == "July") {
            isJuly = true
        }
    }
    return if (isJuly) {
        1 * FeeStructure.FEE_STRUCT.annualCharge
    } else {
        0
    }
}

fun List<MonthFee>.contains(monthName: String): Boolean {
    this.forEach {
        if (it.month == monthName) {
            return true
        }
    }
    return false
}

fun List<MonthFee>.getComputerFee(classID: String): Int {

    var count = 0
    this.forEach { monthFee ->
        if (monthFee.month == "March") {
            count++
        }
        if (monthFee.month == "June") {
            count++
        }
        if (monthFee.month == "September") {
            count++
        }
        if (monthFee.month == "December") {
            count++
        }
    }
    return if (classID.toInt() in 5..7) {
        FeeStructure.FEE_STRUCT.computerFeeJunior * count
    } else if (classID.toInt() in 8..10) {
        FeeStructure.FEE_STRUCT.computerFeeSenior * count
    } else {
        0
    }


}

fun List<Supplement>.getFormattedSupplementString(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("[")
    this.forEach { supplement ->
        stringBuilder.append(supplement.itemName)
        stringBuilder.append(" $INR")
        stringBuilder.append(supplement.price)
        stringBuilder.append(",")
    }
    return "${stringBuilder.toString().substring(0, stringBuilder.length - 1)}]"
}

fun List<Book>.getFormattedBookString(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("[")
    this.forEach { book ->
        stringBuilder.append(book.bookName)
        stringBuilder.append(" $INR")
        stringBuilder.append(book.bookPrice)
        stringBuilder.append(",")
    }
    return "${stringBuilder.toString().substring(0, stringBuilder.length - 1)}]"
}

fun List<MonthFee>.getFormattedMonthString(): String {
    val stringBuilder = StringBuilder()
    stringBuilder.append("[")
    this.forEach { monthFee ->
        stringBuilder.append(monthFee.month)
        stringBuilder.append(",")
    }
    return "${stringBuilder.toString().substring(0, stringBuilder.length - 1)}]"
}

fun String.splitDateTime(): String {
    val values = this.split("at")
    return values[0].trim()
}

fun String.isBookListLong(): Boolean {
    if (this.isNotEmpty()) {
        val result = this.substring(1, this.length - 1).split(",")
        if (result.size > 4) {
            return true
        }
    }
    return false
}

fun String.splitBookList(): ArrayList<String> {
    val list = ArrayList<String>()
    val result = this.substring(1, this.length - 1).split(",")
    val length = result.size
    if (length in 1..4) {
        list.add(result.slice(0 until length).toString())
        list.add("")
        list.add("")

    } else if (length in 5..8) {
        list.add(result.slice(0..3).toString())
        list.add(result.slice(4 until length).toString())
        list.add("")

    } else if (length in 9..12) {
        list.add(result.slice(0..3).toString())
        list.add(result.slice(4..7).toString())
        list.add(result.slice(8 until length).toString())
    }

    return list

}

fun String.isFeeZero(): String {
    this.trim()
    return if (this == "0") {
        ""
    } else {
        this
    }
}

fun String.getMonthName(): ArrayList<String> {
    val list = ArrayList<String>()
    return try {
        val result = this.substring(1, this.length - 1).split(",")
        result.forEach {
            list.add(it)
        }
        list
    } catch (e: StringIndexOutOfBoundsException) {
        list
    }

}







