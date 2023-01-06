package com.example.shoppinglist.pages.add_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.model.DataList
import com.example.shoppinglist.data.model.Groceries
import com.example.shoppinglist.data.model.ListOfGroceries
import com.example.shoppinglist.data.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddListViewModel @Inject constructor(
    private val repository: ShoppingRepository
): ViewModel() {

    private val _state = mutableStateOf(AddListState())
    val state: State<AddListState> = _state
    var listOfGroceries = mutableStateOf<ArrayList<Groceries>>(arrayListOf())
    private var cachedList = arrayListOf<Groceries>()
    private var isSearchStarting = true
    private var isSearching = mutableStateOf(false)

    init {
        ListOfGroceries.listOfGroceries.forEach {
            listOfGroceries.value.add(
                it
            )
        }
    }

    fun searchItem(query: String){

        val listToSearch = if (isSearchStarting){
            listOfGroceries.value
        }else{
            cachedList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                listOfGroceries.value = cachedList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.nameOfGroceries.contains(query.trim(), ignoreCase = true)
            }
            if (isSearchStarting) {
                cachedList = listOfGroceries.value
                isSearchStarting = false
            }
            listOfGroceries.value = results as ArrayList<Groceries>
            isSearching.value = true
        }
    }

    fun onEvent(event: AddListEvent){
        when(event){
            is AddListEvent.ChangeColor -> {
                _state.value = state.value.copy(
                    color = event.color
                )
            }
            is AddListEvent.AddItem -> {
                _state.value = state.value.copy(
                    searchItem = event.item
                )
            }
            is AddListEvent.AddName -> {
                _state.value = state.value.copy(
                    name = event.name
                )
            }
            is AddListEvent.AddToList -> {
                _state.value = state.value.copy(
                    list = addListItem(event.name, event.count)
                )
            }
            is AddListEvent.ReplaceItem -> {
                _state.value = state.value.copy(
                    list = replaceItem(event.name, event.count)
                )
            }

            AddListEvent.SaveToDatabase -> {
                viewModelScope.launch {
                    try {
                        repository.insertList(
                            DataList(
                                name = state.value.name,
                                color = state.value.color,
                                lists = state.value.list,
                                percent = 0f
                            )
                        )
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun addListItem(name: String, count: Int): ArrayList<Groceries>{

        val condition = state.value.list.any {
            it.nameOfGroceries.contains(name)
        }
        if (condition){
            val replace =state.value.list.filter {
                it.nameOfGroceries == name
            }
            replace.forEach {
                it.count += count
            }
        }else{
            state.value.list.add(
                Groceries(
                    nameOfGroceries = name,
                    count = count,
                    type = "kg"
                )
            )
        }
        return state.value.list
    }

    private fun replaceItem(name: String, count: Int): ArrayList<Groceries>{

        val replace =state.value.list.filter {
            it.nameOfGroceries == name
        }
        replace.forEach {
            it.count = count
        }
        return state.value.list
    }
}