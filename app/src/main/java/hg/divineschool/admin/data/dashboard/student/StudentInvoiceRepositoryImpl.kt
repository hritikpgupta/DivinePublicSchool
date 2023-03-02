package hg.divineschool.admin.data.dashboard.student

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class StudentInvoiceRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : StudentInvoiceRepository {

}