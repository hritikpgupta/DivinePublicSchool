package hg.divineschool.admin.data.dashboard.student

import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.StudentMonthFee

interface StudentInvoiceRepository {
    suspend fun getStudent(classID: String, studentScholarNumber: String): Resource<StudentMonthFee>
    suspend fun saveInvoice(classID: String, studentScholarNumber: String, invoice: Invoice): Resource<Invoice>
    suspend fun getAllInvoices(classID: String, studentScholarNumber: String) : List<Invoice>

}