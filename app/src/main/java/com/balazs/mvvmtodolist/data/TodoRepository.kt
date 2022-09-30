package com.balazs.mvvmtodolist.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

interface TodoRepository {


    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getToDoById(id: Int): Todo?

    fun getAllTodos(): Flow<List<Todo>>
}