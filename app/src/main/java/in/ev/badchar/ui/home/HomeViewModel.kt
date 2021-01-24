package `in`.ev.badchar.ui.home

import `in`.ev.badchar.ui.ViewState
import `in`.ev.badchar.ui.home.HomeNavigation.OpenFilterDialog
import `in`.ev.badchar.ui.widgets.SingleLiveEvent
import `in`.ev.domain.model.Character
import `in`.ev.domain.model.Response
import `in`.ev.domain.usecase.GetCharacterUsecase
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeViewModel @ViewModelInject constructor(
    private val getCharacterUseCase: GetCharacterUsecase
) : ViewModel() {
    private val homeNavEvents = SingleLiveEvent<HomeNavigation>()
    val stateHomeEvents: LiveData<HomeNavigation> = homeNavEvents
    private val characterList = MutableLiveData<List<Character>>()
    val observableList: LiveData<List<Character>> = this.characterList
    val showShimmerAnimation = MutableLiveData(true)
    val itemSelected: RecyclerviewItemSelected
        get() = this::characterSelected
    private val characterLiveData: MutableLiveData<ViewState<List<Character>>> = MutableLiveData()
    val stateNav: LiveData<ViewState<List<Character>>> = characterLiveData

    init {
        getBadCharacter()
    }

    private fun getBadCharacter() {
        viewModelScope.launch {
            getCharacterUseCase.execute().collect { response ->
                when (response) {
                    is Response.Loading ->  {
                        adjustShimmerVisibility(response.loading)
                    }
                    is Response.ApiCallSuccess  ->  {
                        response.data?.let {
                            characterLiveData.value = ViewState.Success(it)
                            characterList.apply {
                                value = it
                            }
                        }
                    }
                    is Response.ApiCallError    ->  {
                        characterLiveData.value = ViewState.Failure(response.error)
                    }
                }
            }
        }
    }

    fun showFilterDialog() {
        homeNavEvents.value = OpenFilterDialog
    }

    private fun adjustShimmerVisibility(visibility: Boolean) {
        showShimmerAnimation.value = visibility
    }

    private fun characterSelected(character: Character) {
        homeNavEvents.value = HomeNavigation.NavigateToDetail(character)
    }
}

sealed class HomeNavigation {
    data class NavigateToDetail(val character: Character) : HomeNavigation()
    object OpenFilterDialog : HomeNavigation()
}