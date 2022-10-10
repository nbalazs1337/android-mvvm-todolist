package com.balazs.mvvmtodolist.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.balazs.mvvmtodolist.data.Todo
import com.balazs.mvvmtodolist.data.TodoRepository
import com.balazs.mvvmtodolist.util.Routes
import com.balazs.mvvmtodolist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository:TodoRepository
) : ViewModel(){

    val todos = repository.getAllTodos()
    private var deletedTodo: Todo? = null

    //mmutable version, we can send events into
    private val _uiEvent = Channel<UiEvent>()

    //immutable version, we can't send events into
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TodoListEvent){
        when(event){
            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is TodoListEvent.OnUndoDeleteClick ->{
                deletedTodo?.let {
                    todo -> viewModelScope.launch {
                        repository.insertTodo(todo)
                }
                }
            }
            is TodoListEvent.OnAddTodoClick ->{
               sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }

            is TodoListEvent.OnDeleteTodoClick -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repository.deleteTodo((event.todo))
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Todo deleted",
                        action = "Undo"
                    ))
                }
            }

            is TodoListEvent.OnDoneChange ->{
                viewModelScope.launch {
                    repository.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }

        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch{
            _uiEvent.send(event)
        }
    }

}