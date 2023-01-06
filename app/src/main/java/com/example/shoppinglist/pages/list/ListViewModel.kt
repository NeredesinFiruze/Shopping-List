package com.example.shoppinglist.pages.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: ShoppingRepository
): ViewModel() {

    private val _state = mutableStateOf(ListState())
    val state: State<ListState> = _state

    init {
        getList()
    }

    fun onEvent(event: ListEvent){
        when(event){
            is ListEvent.SearchList -> {
                _state.value = state.value.copy(
                    listName = event.query
                )
            }
            is ListEvent.RemoveList -> {
                viewModelScope.launch {
                    repository.delete(event.list)
                }
            }
            is ListEvent.NavSetup -> {
                _state.value = state.value.copy(
                    navSetup = !state.value.navSetup
                )
            }
            ListEvent.ShowList -> {
                getList()
            }
        }
    }

    private fun getList(){
        viewModelScope.launch {
            _state.value = state.value.copy(
                showList = repository.getAllList()
            )
        }
    }
}