package hg.divineschool.admin.ui.home.setting.transactions

import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import hg.divineschool.admin.data.models.Invoice
import hg.divineschool.admin.data.models.Transaction
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: SettingRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _transactionListFlow = MutableStateFlow<Resource<List<Transaction>>?>(null)
    val transactionListFlow: StateFlow<Resource<List<Transaction>>?> = _transactionListFlow

    private var _invoiceFlow = MutableStateFlow<Resource<Invoice>?>(null)
    val invoiceFlow: StateFlow<Resource<Invoice>?> = _invoiceFlow

    @OptIn(ExperimentalMaterial3Api::class)
    fun getAllTransaction(dateRangeState: DateRangePickerState) = viewModelScope.launch {
        _transactionListFlow.value = Resource.Loading
        _transactionListFlow.value = withContext(ioDispatcher) {
            repository.getTransactions(dateRangeState)
        }
    }

    fun getInvoice(transaction: Transaction) = viewModelScope.launch {
        _invoiceFlow.value = Resource.Loading
        _invoiceFlow.value = withContext(ioDispatcher) {
            repository.getInvoice(transaction)
        }

    }
}