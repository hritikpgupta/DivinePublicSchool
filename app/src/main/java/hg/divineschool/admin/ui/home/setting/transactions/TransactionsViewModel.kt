package hg.divineschool.admin.ui.home.setting.transactions

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

    init {
        getAllTransaction()
    }

    fun getAllTransaction() = viewModelScope.launch {
        _transactionListFlow.value = Resource.Loading
        _transactionListFlow.value = respository.getTransactions()
    }
}