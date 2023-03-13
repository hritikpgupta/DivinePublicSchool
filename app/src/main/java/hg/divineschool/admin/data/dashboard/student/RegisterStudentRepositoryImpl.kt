package hg.divineschool.admin.data.dashboard.student

import android.net.Uri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.data.utils.uploadFile
import hg.divineschool.admin.ui.utils.convertIdToPath
import hg.divineschool.admin.ui.utils.defaultTuitionFeeList
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
                updateStudentList(classId)
                defaultTuitionFeeList.forEach {
                    db.collection("classes").document(classId.convertIdToPath())
                        .collection("students").document(student.scholarNumber.toString())
                        .collection("tuitionFee").document(it.month).set(it).awaitDocument()
                }
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
        student: Student, classId: String, className: String, fileUriString: String
    ): Resource<Student> {
        return try {
            if (fileUriString.isNotEmpty() && fileUriString.startsWith("file:")) {
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
            updateStudentList(classId)
            Resource.Success(student)
        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }
    private suspend fun updateStudentList(classID: String) {
        val result =
            db.collection("classes").document(classID.convertIdToPath()).collection("students")
                .get().awaitDocument()
        var newAdmission = 0
        var studentCount = 0
        var transportStudentCount = 0
        result.documents.let {
            if (it.isNotEmpty()) {
                it.forEach { doc ->
                    if (doc.getBoolean("newStudent") as Boolean) {
                        newAdmission++
                    }
                    if (doc.getBoolean("transportStudent") as Boolean) {
                        transportStudentCount++
                    }
                    if (doc.getBoolean("active") as Boolean) {
                        studentCount++
                    }
                }
            }
        }
        db.collection("classes").document(classID.convertIdToPath()).update(
            mapOf(
                "newAdmission" to newAdmission,
                "studentsCount" to studentCount,
                "transportStudentsCount" to transportStudentCount
            )
        ).awaitDocument()
    }
}