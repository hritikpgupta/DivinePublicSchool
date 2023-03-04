package hg.divineschool.admin.ui.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.DashboardRepository
import hg.divineschool.admin.data.models.ClassInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _classListFlow = MutableStateFlow<Resource<List<ClassInformation>>?>(null)
    val classListFlow: StateFlow<Resource<List<ClassInformation>>?> = _classListFlow

    init {
        getAllClasses()
        getFeeStructure()
    }

    private fun getAllClasses() = viewModelScope.launch {
        _classListFlow.value = Resource.Loading
        val result = repository.getAllClasses()
        _classListFlow.value = result
    }
    private fun getFeeStructure() = viewModelScope.launch {
        repository.getFeeStructure()
    }
}