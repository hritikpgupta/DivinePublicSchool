package hg.divineschool.admin.data.dashboard.student

import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.PendingInvoice
import hg.divineschool.admin.data.utils.awaitDocument
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

    override suspend fun getPendingInvoiceForYear(yearId: String): Resource<List<PendingInvoice>> {
        return try {
            val pendingInvoices = db.collection("pendingDues").document(yearId)
                .collection("transactions").get().awaitDocument()

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
                    invoiceList.add(PendingInvoice(
                        invoice = invoice,
                        remarks = remarks as List<String>
                    ))
                }
                Resource.Success(invoiceList)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

}