package hg.divineschool.admin.ui.home.setting.manageLocation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hg.divineschool.admin.data.Resource
import hg.divineschool.admin.data.dashboard.settings.SettingRepository
import hg.divineschool.admin.data.models.Book
import hg.divineschool.admin.data.models.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ManageTransportLocationViewModel @Inject constructor(
    private val repository: SettingRepository,
) : ViewModel() {

    private val _placeListFlow = MutableStateFlow<Resource<List<Place>>?>(null)
    val placeListFlow: StateFlow<Resource<List<Place>>?> = _placeListFlow

    init {
        getAllPlaces()
    }

    fun getAllPlaces() = viewModelScope.launch {
        _placeListFlow.value = Resource.Loading
        _placeListFlow.value = repository.getAllPlace()
    }

    fun addPlace(place: Place) = viewModelScope.launch {
        _placeListFlow.value = Resource.Loading
        _placeListFlow.value = repository.addPlace(place)
    }

    fun updatePlace(place: Place) = viewModelScope.launch {
        _placeListFlow.value = Resource.Loading
        _placeListFlow.value = repository.updatePlace(place)
    }

    fun deletePlace(place: Place) = viewModelScope.launch {
        _placeListFlow.value = Resource.Loading
        _placeListFlow.value = repository.deletePlace(place)
    }
}