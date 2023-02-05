package hg.divineschool.admin.ui.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    fun loadValue() = viewModelScope.launch {
        firestore.collection("classes").document("classOne").collection("students").document("1480")
            .get()
            .addOnSuccessListener { result ->
                Log.i("TEST", "@@@@@@@@@")
                Log.i("TEST", result.data.toString())
                Log.i("TEST", "@@@@@@@@@")

            }
            .addOnFailureListener { exception ->
                Log.e("TEST", "@@@@@@@@@")
                exception.message?.let { Log.e("TEST", it) }
                Log.e("TEST", "@@@@@@@@@")

            }

    }

    fun login(email: String, password: String) = viewModelScope.launch {
        val auth = Firebase.auth
        val response = auth.signInWithEmailAndPassword(email, password).await()
    }

}