package hg.divineschool.admin.data.dashboard.student

import hg.divineschool.admin.data.Resource

interface PendingInvoiceRepository {

    suspend fun getPendingInvoices(): Resource<List<String>>

}