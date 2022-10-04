package com.balazs.mvvmtodolist.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balazs.mvvmtodolist.data.TodoRepository
import com.balazs.mvvmtodolist.util.Routes
import com.balazs.mvvmtodolist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository:TodoRepository
) : ViewModel(){

    val todos = repository.getAllTodos()

    //mmutable version, we can send events into
    private val _uiEvent = Channel<UiEvent>()

    //immutable version, we can't send events into
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TodoListEvent){
        when(event){
            is TodoListEvent.OnTodoClick -> {

            }
            is TodoListEvent.OnUndoDeleteClick ->{

            }
            is TodoListEvent.OnAddTodoClick ->{
                _uiEvent.send(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }

            is TodoListEvent.OnDeleteTodoClick -> {

            }

            is TodoListEvent.OnDoneChange ->{

            }

        }
    }

    private fun sendUiEvent(event: UiEvent){

    }

}