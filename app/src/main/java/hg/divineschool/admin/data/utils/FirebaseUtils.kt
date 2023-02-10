package hg.divineschool.admin.data.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

suspend fun <T> Task<T>.await(): T {
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if (it.exception != null) {
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }
        }
    }
}

suspend fun <T> Task<T>.awaitDocument(): T {
    return suspendCancellableCoroutine { cont ->
        addOnSuccessListener { doc ->
            if (doc != null) {
                cont.resume(doc, null)
            } else {
                cont.resumeWithException(NullPointerException())
            }
        }.addOnFailureListener {
            cont.resumeWithException(it)
        }
    }
}