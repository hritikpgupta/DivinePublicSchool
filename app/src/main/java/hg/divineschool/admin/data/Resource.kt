package hg.divineschool.admin.data

import java.lang.Exception

sealed class Resource<out R>{
    data class Success<out R>(val result: R): Resource<R>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    data class FailureMessage(val message: String): Resource<Nothing>()
    object Loading: Resource<Nothing>()

}
