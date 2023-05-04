package lr.projects.fashionmakeupapp.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

interface ViewEvent
interface ViewState
interface ViewEffect

abstract class BaseViewModel<EVENT : ViewEvent, STATE : ViewState, EFFECT : ViewEffect>
    : ViewModel() {

    private val _initialState: STATE by lazy { getInitialState() }

    var viewState = mutableStateOf(_initialState)
        private set

    private val _effect = MutableSharedFlow<EFFECT>()
    val effect = _effect.asSharedFlow()

    abstract fun setEvent(event: EVENT)

    protected fun setState(reducer: STATE.() -> STATE) {
        viewState.value = viewState.value.reducer()
    }

    protected fun setEffect(effect: EFFECT) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    abstract fun getInitialState(): STATE

}
