package hg.divineschool.admin.data.dashboard.student

import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.StudentMonthFee

interface StudentInvoiceRepository {
    suspend fun getStudent(classID: String, studentScholarNumber: String): Resource<StudentMonthFee>

}