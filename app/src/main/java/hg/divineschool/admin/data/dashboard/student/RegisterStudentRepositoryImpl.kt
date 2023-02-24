package hg.divineschool.admin.data.dashboard.student

import android.net.Uri
import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.toUpperCase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.data.utils.uploadFile
import hg.divineschool.admin.ui.utils.convertIdToPath
import javax.inject.Inject

class RegisterStudentRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageReference: StorageReference
) : RegisterStudentRepository {

    override suspend fun uploadProfileImage(
        student: Student,
        classId: String,
        className: String,
        fileUriString: String
    ): Resource<Student> {
        val verifiedMessage = validateStudentObjectBeforeUpload(student)
/*        if (verifiedMessage != null){
            return Resource.FailureMessage(verifiedMessage)
        }*/
        return try {
            val fileName =
                student.enrollmentNumber.toString() + "-" + student.firstName+ "-" + student.rollNumber.toString()
            val ref = storageReference.child("$className/$fileName.jpg")
            val downloadUrl = ref.putFile(Uri.parse(fileUriString)).uploadFile(ref)
            student.apply {
                image = downloadUrl.toString()
            }
            db.collection("classes").document(classId.convertIdToPath()).collection("students").document(student.enrollmentNumber.toString()).set(student).awaitDocument()
            Resource.Success(student)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    fun `validateStudentObjectBeforeUpload`(student: Student): String?{
        if (student.contactNumber.toString().trim().length != null){
            return "Contact Number Must Be Of Length 10."
        }
        if (student.entryClass.trim().isEmpty()){
            return "Entry Class Must Not Be Empty."
        }
        if (student.enrollmentNumber.toString().trim().isEmpty()){
            return "Enrollment Number Is Required."
        }
        if (student.firstName.trim().isEmpty()){
            return "First name is required."
        }
        if (student.lastName.trim().isEmpty()){
            return "LastName is required."
        }
        if (student.rollNumber.toString().trim().isEmpty()){
            return "Roll number is required."
        }

        return null
    }

}