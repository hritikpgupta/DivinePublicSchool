package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.student.PendingInvoiceRepository
import hg.divineschool.admin.data.models.PendingInvoice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingDuesViewModel @Inject constructor(
    private val repository: PendingInvoiceRepository
) : ViewModel() {

    private val _pendingInvoiceListFlow = MutableStateFlow<Resource<List<String>>?>(null)
    val pendingInvoiceListFlow: StateFlow<Resource<List<String>>?> = _pendingInvoiceListFlow

    private val _pendingInvoiceFlow = MutableStateFlow<Resource<List<PendingInvoice>>?>(null)
    val pendingInvoiceFlow: StateFlow<Resource<List<PendingInvoice>>?> = _pendingInvoiceFlow

    init {
        getPendingInvoices()
    }

    private fun getPendingInvoices() = viewModelScope.launch {
        _pendingInvoiceListFlow.value = Resource.Loading
        _pendingInvoiceListFlow.value = repository.getPendingInvoices()
    }

    fun getPendingInvoiceForYear(yearId: String) = viewModelScope.launch {
        _pendingInvoiceFlow.value = Resource.Loading
        _pendingInvoiceFlow.value = repository.getPendingInvoiceForYear(yearId)
    }

    fun addRemark(remark: String, invoiceId: String,yearId: String, currentRemarkList: List<String>) = viewModelScope.launch {
        _pendingInvoiceFlow.value = Resource.Loading
        _pendingInvoiceFlow.value = repository.addRemark(remark, invoiceId,yearId,currentRemarkList)
    }

    fun settleInvoice(invoice: PendingInvoice,yearId: String) = viewModelScope.launch {
    }
}