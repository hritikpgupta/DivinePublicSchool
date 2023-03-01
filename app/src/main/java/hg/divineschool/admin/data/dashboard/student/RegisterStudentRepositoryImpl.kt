package hg.divineschool.admin.data.dashboard.student

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.data.utils.uploadFile
import hg.divineschool.admin.ui.utils.Log_Tag
import hg.divineschool.admin.ui.utils.convertIdToPath
import javax.inject.Inject

class RegisterStudentRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore, private val storageReference: StorageReference
) : RegisterStudentRepository {

    override suspend fun uploadProfileImage(
        student: Student, classId: String, className: String, fileUriString: String
    ): Resource<Student> {

        return try {
            val scholarExist =
                db.collection("classes").document(classId.convertIdToPath()).collection("students")
                    .document(student.scholarNumber.toString()).get().awaitDocument()

            if (scholarExist.data == null) {
                if (fileUriString.isNotEmpty()) {
                    val fileName =
                        student.scholarNumber.toString() + "-" + student.firstName + "-" + student.rollNumber.toString()
                    val ref = storageReference.child("$className/$fileName.jpg")
                    val downloadUrl = ref.putFile(Uri.parse(fileUriString)).uploadFile(ref)
                    student.apply {
                        image = downloadUrl.toString()
                    }
                }
                db.collection("classes").document(classId.convertIdToPath()).collection("students")
                    .document(student.scholarNumber.toString()).set(student).awaitDocument()
                Resource.Success(student)
            } else {
                Resource.FailureMessage("Scholar number in use.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun updateProfileImage(
        student: Student,
        classId: String,
        className: String,
        fileUriString: String
    ): Resource<Student> {
        return try {
            if (fileUriString.isNotEmpty() && fileUriString.startsWith("file:")) {
                val fileName = student.scholarNumber.toString() + "-" + student.firstName + "-" + student.rollNumber.toString()
                val ref = storageReference.child("$className/$fileName.jpg")
                val downloadUrl = ref.putFile(Uri.parse(fileUriString)).uploadFile(ref)
                student.apply {
                    image = downloadUrl.toString()
                }
            }
            db.collection("classes").document(classId.convertIdToPath()).collection("students")
                .document(student.scholarNumber.toString()).set(student).awaitDocument()
            Resource.Success(student)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }


}