package hg.divineschool.admin.data.utils

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.FeeStructure
import hg.divineschool.admin.data.models.Place
import hg.divineschool.admin.data.models.Student
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
            cont.resume(doc, null)
        }.addOnFailureListener {
            cont.resumeWithException(it)
        }
    }
}

suspend fun <T> Task<T>.uploadFile(reference: StorageReference): Uri {
    return suspendCancellableCoroutine { cont ->
        continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { it1 -> cont.resumeWithException(it1) }
            }
            reference.downloadUrl
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                cont.resume(it.result, null)
            } else {
                it.exception?.let { it1 -> cont.resumeWithException(it1) }
            }
        }
    }
}

fun `validateStudentObjectBeforeUpload`(student: Student): String? {
    if (student.contactNumber.toString().trim().length != 10) {
        return "Contact Number Must Be Of Length 10."
    }
    if (student.entryClass.trim().isEmpty()) {
        return "Entry Class Must Not Be Empty."
    }
    if (student.scholarNumber.toString().trim().isEmpty()) {
        return "Scholar Number Is Required."
    }
    if (student.firstName.trim().isEmpty()) {
        return "First name is required."
    }
    if (student.lastName.trim().isEmpty()) {
        return "LastName is required."
    }
    if (student.rollNumber.toString().trim().isEmpty()) {
        return "Roll number is required."
    }

    return null
}


fun FeeStructure.getBooks(classID: String): List<Book> {
    val bookList = ArrayList<Book>()
    when (classID.toInt()) {
        0 -> {
            returnBookList(this.pgBooks,bookList)
        }
        1 -> {
            returnBookList(this.lnBooks,bookList)
        }
        2 -> {
            returnBookList(this.unBooks,bookList)
        }
        3 -> {
            returnBookList(this.classOneBooks,bookList)
        }
        4 -> {
            returnBookList(this.classTwoBooks,bookList)
        }
        5 -> {
            returnBookList(this.classThreeBooks,bookList)
        }
        6 -> {
            returnBookList(this.classFourBooks,bookList)
        }
        7 -> {
            returnBookList(this.classFiveBooks,bookList)
        }
        8 -> {
            returnBookList(this.classSixBooks,bookList)
        }
        9 -> {
            returnBookList(this.classSevenBooks,bookList)
        }
        10 -> {
            returnBookList(this.classEightBooks,bookList)
        }
        else ->{
            return bookList
        }
    }
    return bookList
}

fun returnBookList(dataMap: Map<*, *>, bookList: ArrayList<Book>): List<Book> {
    for ((key, value) in dataMap) {
        bookList.add(Book(key.toString(), value.toString().toInt()))
    }
    return bookList
}

fun FeeStructure.getPlaces() : List<Place>{
    val places = ArrayList<Place>()
    for ((key,value) in this.transportPlaces){
        places.add(Place(key.toString(),value.toString().toInt()))
    }
    return places
}
