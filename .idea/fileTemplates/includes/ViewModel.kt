#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME}

#end
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

#parse("File Header.java")
@HiltViewModel
class ${VIEWMODEL} @Inject constructor() : ViewModel() {
    #if (${STATE})
    private val _state = MutableStateFlow<${STATE}>(${STATE}.Loading)
    val state = _state.asStateFlow()
    #end
}