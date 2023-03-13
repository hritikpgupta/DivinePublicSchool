package hg.divineschool.admin.data.dashboard.settings

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.Log_Tag
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(private val db: FirebaseFirestore) :
    SettingRepository {

    override suspend fun migrateUser() {
        moveClassEightStudentFirst()
    }


    private suspend fun moveClassEightStudentFirst() {
        val studentList = db.collection("classes").document("classEight")
            .collection("students").get().awaitDocument()
        Log.i(Log_Tag, "Student List Received ${studentList.documents.size}")
        val studentSet = HashMap<Student, List<Invoice>>()
        studentList.documents.let {
            if (it.isNotEmpty()) {
                it.forEach { doc ->
                    val student = Student(
                        aadharNumber = doc.getLong("aadharNumber") as Long,
                        address = doc.getString("address") as String,
                        contactNumber = doc.getLong("contactNumber") as Long,
                        dateOfAdmission = doc.getString("dateOfAdmission") as String,
                        dateOfBirth = doc.getString("dateOfBirth") as String,
                        scholarNumber = doc.getLong("scholarNumber") as Long,
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
                    val currentStudentInvoice = db.collection("classes").document("classEight")
                        .collection("students").document(student.scholarNumber.toString())
                        .collection("invoices").get().awaitDocument()
                    val invoiceList = ArrayList<Invoice>()
                    currentStudentInvoice.documents.let { invoList ->
                        if (invoList.isNotEmpty()) {
                            invoList.forEach { invo ->
                                invoiceList.add(
                                    Invoice(
                                        invoiceNumber = invo.getString("invoiceNumber") as String,
                                        date = invo.getString("date") as String,
                                        tuitionFeeMonthList = invo.getString("tuitionFeeMonthList") as String,
                                        bookList = invo.getString("bookList") as String,
                                        supplementsList = invo.getString("supplementsList") as String,
                                        admissionFee = invo.getLong("admissionFee") as Long,
                                        annualCharge = invo.getLong("annualCharge") as Long,
                                        computerFee = invo.getLong("computerFee") as Long,
                                        examFee = invo.getLong("examFee") as Long,
                                        lateFee = invo.getLong("lateFee") as Long,
                                        tuitionFee = invo.getLong("tuitionFee") as Long,
                                        transportFee = invo.getLong("transportFee") as Long,
                                        supplementaryFee = invo.getLong("supplementaryFee") as Long,
                                        bookFee = invo.getLong("bookFee") as Long,
                                        total = invo.getLong("total") as Long,
                                        className = invo.getString("className") as String,
                                        studentName = invo.getString("studentName") as String,
                                        scholarNumber = invo.getLong("scholarNumber") as Long,
                                        guardianName = invo.getString("guardianName") as String,
                                        address = invo.getString("address") as String,
                                        rollNumber = invo.getLong("rollNumber") as Long,
                                        placeName = invo.getString("placeName") as String,
                                    )
                                )
                            }
                            studentSet[student] = invoiceList
                            Log.i(Log_Tag, "Added in Student Set ")
                        }
                    }
                }
            }
        }
        studentSet.entries.forEach {

            db.collection("oldStudents").document("classEight").collection("students")
                .document(it.key.scholarNumber.toString()).set(it.key).awaitDocument()
            Log.i(Log_Tag, "Length is ${it.value.size}")
            it.value.forEach { invoice ->

                db.collection("oldStudents").document("classEight").collection("students")
                    .document(it.key.scholarNumber.toString()).collection("invoices")
                    .document(invoice.invoiceNumber).set(invoice).awaitDocument()

            }
        }


    }
}