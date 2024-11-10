#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

#parse("File Header.java")
@HiltViewModel
class ${VIEWMODEL} @Inject constructor() : ViewModel() {
    private val _overviewState = MutableStateFlow<${OVERVIEWSTATE}>(
        ${OVERVIEWSTATE}.Loading
    )
    val overviewState = _overviewState.asStateFlow()
    
    private val _detailState = MutableStateFlow<${DETAILSTATE}>(
        ${DETAILSTATE}.NoSelection
    )
    val detailState = _detailState.asStateFlow()
    
    init {
        loadInitialData()
        
        viewModelScope.launch {
            overviewState.collect {
                _detailState.value = when (it) {
                    is ${OVERVIEWSTATE}.Loading -> ${DETAILSTATE}.NoSelection
                    is ${OVERVIEWSTATE}.Data -> _detailState.value
                    is ${OVERVIEWSTATE}.LoadingMore -> _detailState.value
                    is ${OVERVIEWSTATE}.Error -> ${DETAILSTATE}.Error
                }
            }
        }
    }
    
    private fun loadInitialData() {
        viewModelScope.launch {
        
        }
    }
    
    fun onItemClick(selectedListItem: Selected${NAME}ListItem) {
        if (_overviewState.value !is ${OVERVIEWSTATE}.Data) return
        
        
    }
    
    fun onLoadMore() {
        if (_overviewState.value !is ${OVERVIEWSTATE}.Data) return
    
    
    }
}