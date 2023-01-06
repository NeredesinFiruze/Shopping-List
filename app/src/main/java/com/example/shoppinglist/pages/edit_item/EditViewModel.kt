package com.example.shoppinglist.pages.edit_item

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.model.DataList
import com.example.shoppinglist.data.model.Groceries
import com.example.shoppinglist.data.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val repository: ShoppingRepository,
    savedStateHandle: SavedStateHandle
):ViewModel() {

    private val _state = mutableStateOf(EditState())
    val state: State<EditState> = _state

    val listOfBoolean: ArrayList<Boolean> = ArrayList()
    private var id: Int? = null
    private var name: String = ""
    private var color: Int = -1
    private var list: List<Groceries> = emptyList()

    init {
        savedStateHandle.get<Int>("id")?.let {id->
            viewModelScope.launch {
                if (id != -1){
                    repository.getList(id)?.also { dataList ->
                        this@EditViewModel.id = dataList.id
                        name = dataList.name
                        color = dataList.color
                        list = dataList.lists

                        _state.value = state.value.copy(
                            name = dataList.name,
                            color = dataList.color,
                            list = dataList.lists
                        )
                        _state.value.list.forEach {
                            listOfBoolean += it.isCheck
                        }

                        _state.value = state.value.copy(
                            isCheck = listOfBoolean
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: EditEvent){
        when(event){
            is EditEvent.ChangeColor -> {
                _state.value = state.value.copy(
                    color = event.color
                )
            }
            EditEvent.Save -> {
                val a = state.value.list.size.toDouble()
                val b = state.value.list.filter {
                    it.isCheck
                }.size.toDouble()
                val percent = (b / a)

                viewModelScope.launch {
                    repository.insertList(
                        DataList(
                            id = id,
                            name = name,
                            color = color,
                            lists = list,
                            percent = percent.toFloat()
                        )
                    )
                }
            }
        }
    }
}