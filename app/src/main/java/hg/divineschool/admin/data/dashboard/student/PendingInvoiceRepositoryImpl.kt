package hg.divineschool.admin.data.dashboard.student

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import hg.divineschool.admin.data.Resource
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

}