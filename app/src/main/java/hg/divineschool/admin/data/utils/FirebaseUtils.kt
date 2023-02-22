package hg.divineschool.admin.data.utils

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
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
            cont.resume(doc, null)
        }.addOnFailureListener {
            cont.resumeWithException(it)
        }
    }
}

suspend fun <T> Task<T>.uploadFile(reference: StorageReference): Uri {
    return suspendCancellableCoroutine { cont ->
        continueWithTask{task->
            if (!task.isSuccessful) {
                task.exception?.let { it1 -> cont.resumeWithException(it1) }
            }
            reference.downloadUrl
        }.addOnCompleteListener {
            if (it.isSuccessful){
                cont.resume(it.result,null)
            }else{
                it.exception?.let { it1 -> cont.resumeWithException(it1) }
            }
        }
    }
}

