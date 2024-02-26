package hg.divineschool.admin.data.dashboard.student

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.PendingInvoice
import hg.divineschool.admin.data.utils.awaitDocument
import hg.divineschool.admin.ui.utils.Log_Tag
import hg.divineschool.admin.ui.utils.convertClassNameToId
import hg.divineschool.admin.ui.utils.convertIdToPath
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class PendingInvoiceRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : PendingInvoiceRepository {

    override suspend fun getPendingInvoices(): Resource<List<String>> {
        return try {
            val pendingYearList = mutableListOf<String>()
            val pendingYears = db.collection("pendingDues").get().awaitDocument()
            if (!pendingYears.isEmpty) {
                pendingYears.forEach {
                    pendingYearList.add(it.id)
                }
            }
            Resource.Success(pendingYearList)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun addRemark(
        remark: String, invoiceId: String, yearId: String, currentRemarkList: List<String>
    ): Resource<List<PendingInvoice>> {
        return try {
            val remarkList = currentRemarkList.toMutableList()
            remarkList.add(
                "$remark - ${
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
                }"
            )
            db.collection("pendingDues").document(yearId).collection("transactions")
                .document(invoiceId).update("remarks", remarkList).awaitDocument()
            getPendingInvoiceForYear(yearId)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun getPendingInvoiceForYear(yearId: String): Resource<List<PendingInvoice>> {
        return try {
            val pendingInvoices =
                db.collection("pendingDues").document(yearId).collection("transactions")
                    .whereEqualTo("duesCleared", false)
                    .get()
                    .awaitDocument()

            val invoiceList = ArrayList<PendingInvoice>()

            if (pendingInvoices.isEmpty) {
                Resource.FailureMessage("No pending invoice found for this year $yearId")
            } else {
                pendingInvoices.forEach { info ->
                    val invo = info.get("invoice") as HashMap<*, *>
                    val remarks = info.get("remarks") as List<*>
                    val invoice = Invoice(
                        invoiceNumber = invo["invoiceNumber"] as String,
                        date = invo["date"] as String,
                        tuitionFeeMonthList = invo["tuitionFeeMonthList"] as String,
                        bookList = invo["bookList"] as String,
                        supplementsList = invo["supplementsList"] as String,
                        developmentFee = invo["developmentFee"] as Long,
                        annualCharge = invo["annualCharge"] as Long,
                        computerFee = invo["computerFee"] as Long,
                        examFee = invo["examFee"] as Long,
                        lateFee = invo["lateFee"] as Long,
                        tuitionFee = invo["tuitionFee"] as Long,
                        transportFee = invo["transportFee"] as Long,
                        supplementaryFee = invo["supplementaryFee"] as Long,
                        bookFee = invo["bookFee"] as Long,
                        total = invo["total"] as Long,
                        className = invo["className"] as String,
                        studentName = invo["studentName"] as String,
                        scholarNumber = invo["scholarNumber"] as Long,
                        guardianName = invo["guardianName"] as String,
                        address = invo["address"] as String,
                        rollNumber = invo["rollNumber"] as Long,
                        placeName = invo["placeName"] as String,
                        systemPaid = invo["systemPaid"] as Boolean,
                    )
                    invoiceList.add(
                        PendingInvoice(
                            invoice = invoice, remarks = remarks as List<String>
                        )
                    )
                }
                Resource.Success(invoiceList)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

    override suspend fun settleInvoice(
        invoice: PendingInvoice,
        yearId: String
    ): Resource<List<PendingInvoice>> {
        return try {
            val currentInvoiceClassId = invoice.invoice.className.convertClassNameToId()

            if (currentInvoiceClassId == 10) {
                // Class Eight Student, Take Different Approach
                db.collection("pendingDues").document(yearId).collection("transactions")
                    .document(invoice.invoice.invoiceNumber).update("duesCleared", true).awaitDocument()
            } else {
                for (i in currentInvoiceClassId + 1..9) {
                    val futureClassInvoiceId = i.convertIdToPath()

                    val data = db.collection("classes").document(futureClassInvoiceId)
                        .collection("students")
                        .whereEqualTo("scholarNumber", invoice.invoice.scholarNumber)
                        .get().awaitDocument()
                    if(data != null){
                        db.collection("classes").document(futureClassInvoiceId)
                            .collection("students")
                            .document(invoice.invoice.scholarNumber.toString())
                            .collection("invoices")
                            .document(invoice.invoice.invoiceNumber).update("systemPaid", false)
                            .awaitDocument()

                        db.collection("pendingDues").document(yearId).collection("transactions")
                            .document(invoice.invoice.invoiceNumber).update("duesCleared", true).awaitDocument()
                        break
                    }
                }
            }

            getPendingInvoiceForYear(yearId)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

}