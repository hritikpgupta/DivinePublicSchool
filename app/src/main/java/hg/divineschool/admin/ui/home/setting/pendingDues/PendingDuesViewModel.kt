package hg.divineschool.admin.ui.home.setting.pendingDues

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.student.PendingInvoiceRepository
import hg.divineschool.admin.data.models.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PendingDuesViewModel @Inject constructor(
    private val repository: PendingInvoiceRepository
): ViewModel() {

    private val _pendingInvoiceListFlow = MutableStateFlow<Resource<List<String>>?>(null)
    val pendingInvoiceListFlow: StateFlow<Resource<List<String>>?> = _pendingInvoiceListFlow

    init {
        getPendingInvoices()
    }

    fun getPendingInvoices() = viewModelScope.launch {
        _pendingInvoiceListFlow.value = Resource.Loading
        _pendingInvoiceListFlow.value = repository.getPendingInvoices()
    }
}