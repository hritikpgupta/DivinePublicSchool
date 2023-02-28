package hg.divineschool.admin.data.dashboard.student

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.convertIdToPath
import javax.inject.Inject

class StudentListRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : StudentListRepository {

    private val studentList = ArrayList<Student>()

    override val students: List<Student>
        get() = studentList


    override suspend fun getStudentList(classID: Long): Resource<List<Student>> {
        studentList.clear()
        return try {
            val result =
                db.collection("classes").document(classID.convertIdToPath()).collection("students")
                    .get().awaitDocument()

            result.documents.let {
                if (it.isNotEmpty()) {
                    it.forEach { doc ->
                        studentList.add(
                            Student(
                                aadharNumber = doc.getLong("aadharNumber") as Long,
                                address = doc.getString("address") as String,
                                contactNumber = doc.getLong("contactNumber") as Long,
                                dateOfAdmission = doc.getString("dateOfAdmission") as String,
                                dateOfBirth = doc.getString("dateOfBirth") as String,
                                enrollmentNumber = doc.getLong("enrollmentNumber") as Long,
                                entryClass = doc.getString("entryClass") as String,
                                fathersName = doc.getString("fathersName") as String,
                                firstName = doc.getString("firstName") as String,
                                gender = doc.getString("gender") as String,
                                guardianOccupation = doc.getString("guardianOccupation") as String,
                                image = doc.getString("image") as String,
                                orphan = doc.getBoolean("orphan") as Boolean,
                                lastName = doc.getString("lastName") as String,
                                mothersName = doc.getString("mothersName") as String,
                                newStudent = doc.getBoolean("newStudent") as Boolean,
                                religion = doc.getString("religion") as String,
                                rollNumber = doc.getLong("rollNumber") as Long,
                                schoolAttended = doc.getString("schoolAttended") as String,
                                transportStudent = doc.getBoolean("transportStudent") as Boolean,
                                rte = doc.getBoolean("rte") as Boolean,
                                active = doc.getBoolean("active") as Boolean
                            )
                        )
                    }
                }
            }
            Resource.Success(studentList)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }
}