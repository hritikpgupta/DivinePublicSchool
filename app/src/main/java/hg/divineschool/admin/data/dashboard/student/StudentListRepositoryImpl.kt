package hg.divineschool.admin.data.dashboard.student

import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.convertIdToPath
import javax.inject.Inject

class StudentListRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : StudentListRepository {

    override suspend fun getStudentList(classID: Long): Resource<List<Student>> {
        return try {
            val result =
                db.collection("classes").document(classID.convertIdToPath()).collection("students")
                    .get().awaitDocument()
            val studentList = ArrayList<Student>()
            result.let {
                if (!it.isEmpty) {
                    it.forEach { doc ->
                        studentList.add(
                            Student(
                                aadharNumber = doc.get("aadharNumber") as Long,
                                address = doc.get("address") as String,
                                contactNumber = doc.get("contactNumber") as Long,
                                dateOfAdmission = doc.get("dateOfAdmission") as String,
                                dateOfBirth = doc.get("dateOfBirth") as String,
                                enrollmentNumber = doc.get("enrollmentNumber") as Long,
                                entryClass = doc.get("entryClass") as String,
                                fathersName = doc.get("fathersName") as String,
                                firstName = doc.get("firstName") as String,
                                gender = doc.get("gender") as String,
                                guardianOccupation = doc.get("guardianOccupation") as String,
                                image = doc.get("image") as String,
                                isOrphan = doc.get("isOrphan") as Boolean,
                                lastName = doc.get("lastName") as String,
                                mothersName = doc.get("mothersName") as String,
                                newStudent = doc.get("newStudent") as Boolean,
                                religion = doc.get("religion") as String,
                                rollNumber = doc.get("rollNumber") as Long,
                                schoolAttended = doc.get("schoolAttended") as String,
                                transportStudent = doc.get("transportStudent") as Boolean
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