package lr.projects.fashionmakeupapp.ui.makeuplist

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import lr.projects.fashionmakeupapp.model.Product
import lr.projects.fashionmakeupapp.data.repositories.MakeUpRepository
import lr.projects.fashionmakeupapp.data.remote.network.NetworkResult
import lr.projects.fashionmakeupapp.ui.BaseViewModel
import lr.projects.fashionmakeupapp.ui.ViewEffect
import lr.projects.fashionmakeupapp.ui.ViewEvent
import lr.projects.fashionmakeupapp.ui.ViewState
import javax.inject.Inject

@HiltViewModel
class MakeUpListViewModel @Inject constructor(
    private val makeUpRepository: MakeUpRepository
) : BaseViewModel<
        MakeUpListViewModel.UiEvent,
        MakeUpListViewModel.UiState,
        MakeUpListViewModel.UiEffect>()
{
    init {
        viewModelScope.launch {
            when(val response = makeUpRepository.getProducts()){
                is NetworkResult.Success -> {
                    setState {
                        copy(makeUpList= response.data, isLoading = false)
                    }
                }
                is NetworkResult.ApiError,
                is NetworkResult.ApiException -> {
                    setEffect((UiEffect.ShowError))
                    setState { copy(isLoading = false) }
                }
            }
        }
    }

    override fun getInitialState() = UiState()

    override fun setEvent(event: UiEvent){
        when(event){
            is UiEvent.ItemSelected -> {
                setEffect(UiEffect.Navigate("Detail/${event.itemId}"))
            }
        }
    }

    data class UiState(
        val makeUpList: List<Product> = emptyList(),
        val isLoading : Boolean = true
    ): ViewState

    sealed class UiEffect :ViewEffect{
        object ShowError : UiEffect()
        data class Navigate(val route : String) : UiEffect()
    }

    sealed class UiEvent : ViewEvent {
        data class ItemSelected(val itemId: String) : UiEvent()
    }
}