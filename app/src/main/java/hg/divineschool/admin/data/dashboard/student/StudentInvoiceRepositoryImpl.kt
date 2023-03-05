package hg.divineschool.admin.data.dashboard.student

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.MonthFee
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.models.StudentMonthFee
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.convertIdToPath
import javax.inject.Inject

class StudentInvoiceRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : StudentInvoiceRepository {
    override suspend fun getStudent(
        classID: String,
        studentScholarNumber: String
    ): Resource<StudentMonthFee> {
        return try {
            val monthFeeList = ArrayList<MonthFee>()
            val result = db.collection("classes").document(classID.convertIdToPath())
                .collection("students").document(studentScholarNumber).get().awaitDocument()
            val studentFeeList = db.collection("classes").document(classID.convertIdToPath())
                .collection("students").document(studentScholarNumber).collection("tuitionFee")
                .orderBy("monthIndex", Query.Direction.ASCENDING)
                .get().awaitDocument()


            val stu = Student()
            result.data.let {
                stu.apply {
                    it.let { value ->
                        lastName = value?.get("lastName") as String
                        image = value["image"] as String
                        schoolAttended = value["schoolAttended"] as String
                        guardianOccupation = value["guardianOccupation"] as String
                        transportStudent = value["transportStudent"] as Boolean
                        rte = value["rte"] as Boolean
                        address = value["address"] as String
                        gender = value["gender"] as String
                        fathersName = value["fathersName"] as String
                        active = value["active"] as Boolean
                        dateOfBirth = value["dateOfBirth"] as String
                        newStudent = value["newStudent"] as Boolean
                        scholarNumber = value["scholarNumber"] as Long
                        entryClass = value["entryClass"] as String
                        orphan = value["orphan"] as Boolean
                        religion = value["religion"] as String
                        firstName = value["firstName"] as String
                        aadharNumber = value["aadharNumber"] as Long
                        mothersName = value["mothersName"] as String
                        contactNumber = value["contactNumber"] as Long
                        rollNumber = value["rollNumber"] as Long
                        dateOfAdmission = value["dateOfAdmission"] as String
                    }
                }
            }
            studentFeeList.documents.let {
                if (it.isNotEmpty()) {
                    it.forEach { doc ->
                        monthFeeList.add(
                            MonthFee(
                                isPaid = doc.getBoolean("paid") as Boolean,
                                month = doc.getString("month") as String,
                                monthIndex = doc.getLong("monthIndex") as Long
                            )
                        )
                    }
                }
            }
            val studentMonthFee = StudentMonthFee(student = stu, monthFeeList = monthFeeList)

            Resource.Success(studentMonthFee)

        } catch (e: Exception) {
            Resource.Failure(e)
        }
    }

}