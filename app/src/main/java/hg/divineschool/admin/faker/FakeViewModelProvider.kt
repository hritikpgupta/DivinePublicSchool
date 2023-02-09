package hg.divineschool.admin.faker

import com.google.firebase.auth.FirebaseUser
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.auth.AuthRepository
import hg.divineschool.admin.ui.auth.AuthViewModel

/*
* Currently there is a problem with *Jetpack Compose Preview* & *Hilt*
* Jetpack compose is not able to inject using hiltViewModel() to generate Compose Previews
* In future when both these libraries will be compatible, we can remove this object
* But for now, to see preview, we can use this FakeViewModelProvider
* */
object FakeViewModelProvider {


    fun provideAuthViewModel() = AuthViewModel(authRepo)

    private val authRepo = object : AuthRepository {
        override val currentUser: FirebaseUser?
            get() = TODO("Not yet implemented")

        override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
            TODO("Not yet implemented")
        }

        override fun logout() {
            TODO("Not yet implemented")
        }

    }

}