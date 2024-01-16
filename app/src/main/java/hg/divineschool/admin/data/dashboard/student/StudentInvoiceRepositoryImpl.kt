package hg.divineschool.admin.data.dashboard.student

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.*
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.convertIdToPath
import hg.divineschool.admin.ui.utils.getMonthName
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

    override suspend fun saveInvoice(
        classID: String,
        studentScholarNumber: String,
        invoice: Invoice
    ): Resource<Invoice> {
        return try {
            val invoiceList =
                db.collection("classes").document(classID.convertIdToPath())
                    .collection("students").document(studentScholarNumber).collection("invoices")
                    .get().awaitDocument()

            invoiceList.documents.let {
                if (it.size == 0) {
                    db.collection("classes").document(classID.convertIdToPath())
                        .collection("students").document(studentScholarNumber)
                        .collection("invoices")
                        .document(invoice.invoiceNumber).set(invoice).awaitDocument()
                } else {
                    invoice.invoiceNumber = "${studentScholarNumber}-${it.size + 1}"
                    db.collection("classes").document(classID.convertIdToPath())
                        .collection("students").document(studentScholarNumber)
                        .collection("invoices")
                        .document(invoice.invoiceNumber).set(invoice).awaitDocument()
                }
                val monthList = invoice.tuitionFeeMonthList.getMonthName()
                monthList.forEach { month ->
                    db.collection("classes").document(classID.convertIdToPath())
                        .collection("students")
                        .document(studentScholarNumber)
                        .collection("tuitionFee").document(month.trim()).update("paid", true)
                        .awaitDocument()
                }
                val transaction = Transaction(
                    amount = invoice.total,
                    className = invoice.className,
                    scholarNumber = studentScholarNumber,
                    invoiceNumber = invoice.invoiceNumber,
                    studentName = invoice.studentName,
                    timestamp = Timestamp.now()
                )

                db.collection("transactions").document(invoice.invoiceNumber).set(transaction)
                    .awaitDocument()

            }
            Resource.Success(invoice)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getAllInvoices(
        classID: String,
        studentScholarNumber: String
    ): Resource<List<Invoice>> {
        return try {
            val invoiceList = ArrayList<Invoice>()
            val result = db.collection("classes").document(classID.convertIdToPath())
                .collection("students").document(studentScholarNumber).collection("invoices")
                .orderBy("invoiceNumber", Query.Direction.DESCENDING)
                .get().awaitDocument()
            result.documents.let {

                if (it.isNotEmpty()) {
                    it.forEach { invo ->
                        invoiceList.add(
                            Invoice(
                                invoiceNumber = invo.getString("invoiceNumber") as String,
                                date = invo.getString("date") as String,
                                tuitionFeeMonthList = invo.getString("tuitionFeeMonthList") as String,
                                bookList = invo.getString("bookList") as String,
                                supplementsList = invo.getString("supplementsList") as String,
                                developmentFee = invo.getLong("developmentFee") as Long,
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
                }
            }
            return Resource.Success(invoiceList)
        } catch (e: Exception) {
            Resource.Failure(e)
        }


    }

}