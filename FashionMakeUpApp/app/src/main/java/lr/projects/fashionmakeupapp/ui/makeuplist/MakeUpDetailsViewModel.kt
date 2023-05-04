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
class MakeUpDetailsViewModel @Inject constructor(
    private val makeUpRepository: MakeUpRepository
) : BaseViewModel<
        MakeUpDetailsViewModel.UiEvent,
        MakeUpDetailsViewModel.UiState,
        MakeUpDetailsViewModel.UiEffect>()
{
    suspend fun getProduct(idProduct: String) {
        viewModelScope.launch {
            makeUpRepository
                .getProductsById(idProduct)
                .collect { result ->
                    when (result) {
                        is NetworkResult.ApiError, is NetworkResult.ApiException -> {
                            setEffect((MakeUpDetailsViewModel.UiEffect.ShowError))
                            setState { copy(isLoading = false) }
                        }

                        is NetworkResult.Success -> {
                            setState {
                                copy(makeUpProduct = result.data, isLoading = false)
                            }
                        }
                    }
                }
        }
    }

    override fun getInitialState() = UiState()

    override fun setEvent(event: UiEvent){
        when(event){
            is UiEvent.GoBack -> {
                setEffect(UiEffect.GoBack)
            }
        }
    }

    data class UiState(
        val makeUpProduct: Product = Product(),
        val isLoading : Boolean = true
    ): ViewState

    sealed class UiEffect :ViewEffect{
        object ShowError : UiEffect()
        object GoBack : UiEffect()
    }

    sealed class UiEvent : ViewEvent {
        object GoBack : UiEvent()
    }
}