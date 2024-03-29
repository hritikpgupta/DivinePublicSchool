package hg.divineschool.admin.data.auth

import com.google.firebase.auth.FirebaseUser
import hg.divineschool.admin.data.Resource

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser>
    fun logout()
}