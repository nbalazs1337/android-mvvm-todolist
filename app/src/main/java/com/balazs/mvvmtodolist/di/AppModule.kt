package com.balazs.mvvmtodolist.di

import android.app.Application
import androidx.room.Room
import com.balazs.mvvmtodolist.data.TodoDatabase
import com.balazs.mvvmtodolist.data.TodoRepository
import com.balazs.mvvmtodolist.data.TodoRepositoryImpl
import dagger.Provides
import javax.inject.Singleton

object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application):TodoDatabase{
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase) : TodoRepository{
        return TodoRepositoryImpl(db.dao)
    }
}