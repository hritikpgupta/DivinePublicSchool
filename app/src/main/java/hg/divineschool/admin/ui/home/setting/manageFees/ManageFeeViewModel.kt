package hg.divineschool.admin.ui.home.setting.manageFees

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import hg.divineschool.admin.data.models.FeeStructure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ManageFeeViewModel @Inject constructor(
    private val repository: SettingRepository,
) : ViewModel() {
    private var _priceUpdateFlow = MutableStateFlow<Resource<Boolean>?>(null)
    val priceUpdateFlow: StateFlow<Resource<Boolean>?> = _priceUpdateFlow

    fun updatePrice(feeStructure: FeeStructure) = viewModelScope.launch {
        _priceUpdateFlow.value = Resource.Loading
        _priceUpdateFlow.value = repository.updatePrice(feeStructure)

    }
}