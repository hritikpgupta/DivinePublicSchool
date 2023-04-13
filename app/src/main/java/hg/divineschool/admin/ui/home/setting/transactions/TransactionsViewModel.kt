package hg.divineschool.admin.ui.home.setting.transactions

import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import hg.divineschool.admin.data.models.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private var respository: SettingRepository
) : ViewModel() {

    private var _transactionListFlow = MutableStateFlow<Resource<List<Transaction>>?>(null)
    val transactionListFlow: StateFlow<Resource<List<Transaction>>?> = _transactionListFlow

    @OptIn(ExperimentalMaterial3Api::class)
    fun getAllTransaction(dateRangeState : DateRangePickerState) = viewModelScope.launch {
        _transactionListFlow.value = Resource.Loading
        _transactionListFlow.value = respository.getTransactions(dateRangeState)
    }
}