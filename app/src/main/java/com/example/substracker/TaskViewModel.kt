package com.example.substracker

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TaskViewModel inherits from AndroidViewModel, which is lifecycle-aware and holds data for the UI.
// It receives the Application context to access the database.
class TaskViewModel(application: Application) : AndroidViewModel(application) {
    /* allTasks: This is a LiveData list of all tasks. It allows your UI
    (like a RecyclerView) to observe changes and auto-update when data changes*/
    val allTasks: LiveData<List<Task>>
    private val repository: TaskRepository // repository: Acts as a bridge between the ViewModel and the Room database.

    /* Gets the TaskDao (Data Access Object) from the singleton Room database.
    Initializes the repository with the DAO.
    Sets allTasks to the LiveData provided by the repository. */
    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }

    /* Inserts a task into the database.
    viewModelScope: Coroutine scope tied to ViewModel lifecycle (automatically cancelled when ViewModel is destroyed).
    Dispatchers.IO: Used for database/network operations (runs on a background thread). */
    fun insert(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(task)
        }
    }

    // Updates a task in the database, also on a background thread.
    fun update(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(task)
        }
    }

    // delete a task from the database
    fun delete(task: Task) {
        viewModelScope.launch(Dispatchers.IO){
            repository.delete(task)
        }
    }
}

/* ViewModel: Stores and manages UI-related data.

It survives config changes (like screen rotation).

Works with the repository to access data.

Provides LiveData for observing data in the UI. */