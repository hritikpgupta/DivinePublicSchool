package hg.divineschool.admin.data.dashboard.student

import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.utils.awaitDocument
import javax.inject.Inject

class PendingInvoiceRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : PendingInvoiceRepository {

    override suspend fun getPendingInvoices(): Resource<List<String>> {
        return try {
            val pendingYearList = emptyList<String>()
            val pendingYears = db.collection("pendingDues").get().awaitDocument()
            if (!pendingYears.isEmpty) {
                pendingYears.forEach {
                    pendingYearList.plus(it.id)
                }
            }
            Resource.Success(pendingYearList)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failure(e)
        }
    }

}