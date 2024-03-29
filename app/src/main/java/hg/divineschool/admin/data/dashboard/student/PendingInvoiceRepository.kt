package hg.divineschool.admin.data.dashboard.student

import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.models.PendingInvoice

interface PendingInvoiceRepository {

    suspend fun getPendingInvoices(): Resource<List<String>>

    suspend fun getPendingInvoiceForYear(yearId : String): Resource<List<PendingInvoice>>

    suspend fun addRemark(remark: String, invoiceId: String,yearId: String, currentRemarkList: List<String>) : Resource<List<PendingInvoice>>

    suspend fun settleInvoice(invoice: PendingInvoice,yearId: String) : Resource<List<PendingInvoice>>

}