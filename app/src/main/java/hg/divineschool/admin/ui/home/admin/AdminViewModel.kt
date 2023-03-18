package hg.divineschool.admin.ui.home.admin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.Student
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.Log_Tag
import hg.divineschool.admin.ui.utils.defaultTuitionFeeList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class MigrationEvent {
    data class SendLog(val msg: String) : MigrationEvent()
    data class Failure(val e: java.lang.Exception) : MigrationEvent()
    data class Success(val boolean: Boolean) : MigrationEvent()
}

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val db: FirebaseFirestore

) : ViewModel() {

    private val dbMigrationEventChannel = Channel<MigrationEvent>()
    val migrationEvent = dbMigrationEventChannel.receiveAsFlow()

    init {
        checkIfAllStudentsHavePaidTuitionFee()
    }

    private fun `checkIfAllStudentsHavePaidTuitionFee`() = viewModelScope.launch {
        dbMigrationEventChannel.send(MigrationEvent.SendLog("Checking if all classes have paid their dues"))
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class 8 students have paid"))
        val hasClassEightPaid = checkIfAClassHasPaid("classEight")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class 7 students have paid"))
        val hasClassSevenPaid = checkIfAClassHasPaid("classSeven")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class 6 students have paid"))
        val hasClassSixPaid = checkIfAClassHasPaid("classSix")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class 5 students have paid"))
        val hasClassFivePaid = checkIfAClassHasPaid("classFive")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class 4 students have paid"))
        val hasClassFourPaid = checkIfAClassHasPaid("classFour")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class 3 students have paid"))
        val hasClassThreePaid = checkIfAClassHasPaid("classThree")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class 2 students have paid"))
        val hasClassTwoPaid = checkIfAClassHasPaid("classTwo")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class 1 students have paid"))
        val hasClassOnePaid = checkIfAClassHasPaid("classOne")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class U.N students have paid"))
        val hasClassUNPaid = checkIfAClassHasPaid("classUpperNursery")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class L.N students have paid"))
        val hasClassLNPaid = checkIfAClassHasPaid("classLowerNursery")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("--Checking if all Class Play Group students have paid"))
        val hasClassPGPaid = checkIfAClassHasPaid("classPlayGroup")
        dbMigrationEventChannel.send(MigrationEvent.SendLog("Finished Checking Dues For All Classes"))

        if (hasClassEightPaid && hasClassSevenPaid && hasClassSixPaid && hasClassFivePaid && hasClassFourPaid && hasClassThreePaid && hasClassTwoPaid && hasClassOnePaid
            && hasClassUNPaid && hasClassLNPaid && hasClassPGPaid
        )
        {
            migrate()
        }
        else {
            if (!hasClassEightPaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class Eight have not paid their dues."))
            }
            if (!hasClassSevenPaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class Seven have not paid their dues."))
            }
            if (!hasClassSixPaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class Six have not paid their dues."))
            }
            if (!hasClassFivePaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class Five have not paid their dues."))
            }
            if (!hasClassFourPaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class Four have not paid their dues."))
            }
            if (!hasClassThreePaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class Three have not paid their dues."))
            }
            if (!hasClassTwoPaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class Two have not paid their dues."))
            }
            if (!hasClassOnePaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class One have not paid their dues."))
            }
            if (!hasClassUNPaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class UN have not paid their dues."))
            }
            if (!hasClassLNPaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class LN have not paid their dues."))
            }
            if (!hasClassPGPaid){
                dbMigrationEventChannel.send(MigrationEvent.SendLog("---Some Students in Class PG have not paid their dues."))
            }

        }

    }

    private suspend fun checkIfAClassHasPaid(className: String): Boolean {
        val classStudentList =
            db.collection("classes").document(className).collection("students").get()
                .awaitDocument()
        classStudentList.documents.let { studentList ->
            if (studentList.isNotEmpty()) {
                studentList.forEach { doc ->
                    val scholarNum = doc.getLong("scholarNumber") as Long
                    val currentStudentTuitionFee =
                        db.collection("classes").document(className).collection("students")
                            .document(scholarNum.toString()).collection("tuitionFee")
                            .get().awaitDocument()
                    currentStudentTuitionFee.documents.let { it ->
                        if (it.isNotEmpty()) {
                            it.forEach { doc ->
                                if (!(doc.getBoolean("paid") as Boolean)) {
                                    return false
                                }
                            }
                        }
                    }
                }
            }
        }
        return true
    }

    private fun migrate() = viewModelScope.launch {
        try {
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Eight Students to Old Collection"))
            moveAndDeleteClassEightStudentFirst()
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Seven Students to Class Eight"))
            moveAndDeleteStudentsFromOneClassToAnother("classSeven", "classEight")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Six Students to Class Seven"))
            moveAndDeleteStudentsFromOneClassToAnother("classSix", "classSeven")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Five Students to Class Six"))
            moveAndDeleteStudentsFromOneClassToAnother("classFive", "classSix")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Four Students to Class Five"))
            moveAndDeleteStudentsFromOneClassToAnother("classFour", "classFive")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Three Students to Class Four"))
            moveAndDeleteStudentsFromOneClassToAnother("classThree", "classFour")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Two Students to Class Three"))
            moveAndDeleteStudentsFromOneClassToAnother("classTwo", "classThree")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class One Students to Class Two"))
            moveAndDeleteStudentsFromOneClassToAnother("classOne", "classTwo")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Upper Nursery Students to Class One"))
            moveAndDeleteStudentsFromOneClassToAnother("classUpperNursery", "classOne")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Lower Nursery Students to Class Upper Nursery"))
            moveAndDeleteStudentsFromOneClassToAnother("classLowerNursery", "classUpperNursery")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Started Moving Class Play Group Students to Class Lower Nursery"))
            moveAndDeleteStudentsFromOneClassToAnother("classPlayGroup", "classLowerNursery")
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Database Migration Finished."))

        } catch (e: Exception) {
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Database Migration Failed with error ${e.message}"))
            dbMigrationEventChannel.send(MigrationEvent.Failure(e))
        }

    }

    private suspend fun moveAndDeleteClassEightStudentFirst() {
        try {
            val studentList =
                db.collection("classes").document("classEight").collection("students").get()
                    .awaitDocument()
            dbMigrationEventChannel.send(MigrationEvent.SendLog("--Getting all Class Eight Students"))
            val studentSet = HashMap<Student, List<Invoice>>()
            // Creating DataSet
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
                            active = false
                        )
                        val currentStudentInvoice =
                            db.collection("classes").document("classEight").collection("students")
                                .document(student.scholarNumber.toString()).collection("invoices")
                                .get().awaitDocument()
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
            // Copy Values to Old Student List
            dbMigrationEventChannel.send(MigrationEvent.SendLog("--Copying all Class Eight Students to old collection"))
            studentSet.entries.forEach {
                db.collection("oldStudents").document("classEight").collection("students")
                    .document(it.key.scholarNumber.toString()).set(it.key).awaitDocument()
                it.value.forEach { invoice ->
                    db.collection("oldStudents").document("classEight").collection("students")
                        .document(it.key.scholarNumber.toString()).collection("invoices")
                        .document(invoice.invoiceNumber).set(invoice).awaitDocument()
                }
            }
            //Remove Values From Class Eight
            dbMigrationEventChannel.send(MigrationEvent.SendLog("--Clearing Class Eight Collection"))
            studentSet.entries.forEach {
                db.collection("classes").document("classEight").collection("students")
                    .document(it.key.scholarNumber.toString()).delete().awaitDocument()
                it.value.forEach { invoice ->
                    db.collection("classes").document("classEight").collection("students")
                        .document(it.key.scholarNumber.toString()).collection("invoices")
                        .document(invoice.invoiceNumber).delete().awaitDocument()
                }
                val tuitionFee =
                    db.collection("classes").document("classEight").collection("students")
                        .document(it.key.scholarNumber.toString()).collection("tuitionFee").get()
                        .awaitDocument()
                tuitionFee.documents.let { tf ->
                    if (tf.isNotEmpty()) {
                        tf.forEach { tfo ->
                            db.collection("classes").document("classEight").collection("students")
                                .document(it.key.scholarNumber.toString()).collection("tuitionFee")
                                .document(tfo.id).delete().awaitDocument()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Error occurred migrating class eight students to old collection"))
            throw InterruptedException()
        }
    }

    private suspend fun moveAndDeleteStudentsFromOneClassToAnother(
        fromClass: String,
        toClass: String
    ) {
        try {
            val studentList =
                db.collection("classes").document(fromClass).collection("students").get()
                    .awaitDocument()
            dbMigrationEventChannel.send(MigrationEvent.SendLog("--Getting all $fromClass Students"))
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
                        val currentStudentInvoice =
                            db.collection("classes").document(fromClass).collection("students")
                                .document(student.scholarNumber.toString()).collection("invoices")
                                .get().awaitDocument()
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
                            }
                            studentSet[student] = invoiceList
                        }
                    }
                }
            }
            dbMigrationEventChannel.send(MigrationEvent.SendLog("--Copying all $fromClass Students to $toClass"))
            studentSet.entries.forEach {
                if (it.key.active) {
                    // Copy Values to Destination Class
                    db.collection("classes").document(toClass).collection("students")
                        .document(it.key.scholarNumber.toString()).set(it.key).awaitDocument()
                    it.value.forEach { invoice ->
                        db.collection("classes").document(toClass).collection("students")
                            .document(it.key.scholarNumber.toString()).collection("invoices")
                            .document(invoice.invoiceNumber).set(invoice).awaitDocument()
                    }
                    // Add new month list
                    defaultTuitionFeeList.forEach { m ->
                        db.collection("classes").document(toClass)
                            .collection("students").document(it.key.scholarNumber.toString())
                            .collection("tuitionFee").document(m.month).set(m).awaitDocument()
                    }
                } else {
                    // Copy Left Student to Old Student List (Source Class)
                    db.collection("oldStudents").document(fromClass).collection("students")
                        .document(it.key.scholarNumber.toString()).set(it.key).awaitDocument()
                    it.value.forEach { invoice ->
                        db.collection("oldStudents").document(fromClass).collection("students")
                            .document(it.key.scholarNumber.toString()).collection("invoices")
                            .document(invoice.invoiceNumber).set(invoice).awaitDocument()
                    }
                }
            }
            dbMigrationEventChannel.send(MigrationEvent.SendLog("--Clearing $fromClass Collection"))
            studentSet.entries.forEach {
                db.collection("classes").document(fromClass).collection("students")
                    .document(it.key.scholarNumber.toString()).delete().awaitDocument()
                it.value.forEach { invoice ->
                    db.collection("classes").document(fromClass).collection("students")
                        .document(it.key.scholarNumber.toString()).collection("invoices")
                        .document(invoice.invoiceNumber).delete().awaitDocument()
                }
                val tuitionFee =
                    db.collection("classes").document(fromClass).collection("students")
                        .document(it.key.scholarNumber.toString()).collection("tuitionFee").get()
                        .awaitDocument()
                tuitionFee.documents.let { tf ->
                    if (tf.isNotEmpty()) {
                        tf.forEach { tfo ->
                            db.collection("classes").document(fromClass).collection("students")
                                .document(it.key.scholarNumber.toString()).collection("tuitionFee")
                                .document(tfo.id).delete().awaitDocument()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            dbMigrationEventChannel.send(MigrationEvent.SendLog("Error occurred migrating $fromClass students to $toClass"))
            throw InterruptedException()
        }

    }

}