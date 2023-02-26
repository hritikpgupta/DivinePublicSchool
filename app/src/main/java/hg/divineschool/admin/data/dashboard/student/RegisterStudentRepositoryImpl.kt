package hg.divineschool.admin.data.dashboard.student

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
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
        fileUriString: String,
        isUpdate: Boolean
    ): Resource<Student> {

        return try {
            val enrollmentExist = db.collection("classes")
                .document(classId.convertIdToPath())
                .collection("students")
                .document(student.enrollmentNumber.toString()).get().awaitDocument()

            if (enrollmentExist.data == null) {
                if (fileUriString.isNotEmpty()) {
                    val fileName =
                        student.enrollmentNumber.toString() + "-" + student.firstName + "-" + student.rollNumber.toString()
                    val ref = storageReference.child("$className/$fileName.jpg")
                    val downloadUrl = ref.putFile(Uri.parse(fileUriString)).uploadFile(ref)
                    student.apply {
                        image = downloadUrl.toString()
                    }
                }
                db.collection("classes")
                    .document(classId.convertIdToPath())
                    .collection("students")
                    .document(student.enrollmentNumber.toString())
                    .set(student).awaitDocument()
                Resource.Success(student)
            } else {
                if (isUpdate) {
                    if (fileUriString.isNotEmpty()) {
                        val fileName =
                            student.enrollmentNumber.toString() + "-" + student.firstName + "-" + student.rollNumber.toString()
                        val ref = storageReference.child("$className/$fileName.jpg")
                        val downloadUrl = ref.putFile(Uri.parse(fileUriString)).uploadFile(ref)
                        student.apply {
                            image = downloadUrl.toString()
                        }
                    }
                    db.collection("classes")
                        .document(classId.convertIdToPath())
                        .collection("students")
                        .document(student.enrollmentNumber.toString())
                        .set(student).awaitDocument()
                    Resource.Success(student)
                } else {
                    Resource.FailureMessage("Scholar number in use.")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }


}