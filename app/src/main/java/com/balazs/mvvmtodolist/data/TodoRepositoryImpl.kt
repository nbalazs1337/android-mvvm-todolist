package com.balazs.mvvmtodolist.data

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val dao:TodoDao) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
       dao.deleteTodo(todo)
    }

    override suspend fun getToDoById(id: Int): Todo? {
        return dao.getToDoById(id)
    }

    override fun getAllTodos(): Flow<List<Todo>> {
        return dao.getAllTodos()
    }
}