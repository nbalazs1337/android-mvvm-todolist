package com.balazs.mvvmtodolist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getToDoById(id: Int): Todo?

    @Query("SELECT * FROM todo")
    fun getAllTodos(): Flow<List<Todo>>

}