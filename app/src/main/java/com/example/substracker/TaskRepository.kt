package com.example.substracker

import androidx.lifecycle.LiveData

/* The repository takes a TaskDao as a constructor parameter.

It acts as a single source of truth for your data (abstracting access from DB or even network). */
class TaskRepository(private val taskDao: TaskDao) {
    /* This is the list of all tasks stored in the Room database, wrapped in LiveData.
    The UI (via ViewModel) can observe this and update the task list in real-time. */
    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    /* Calls the DAO’s insert function.
    suspend: Because Room operations must be run on a background thread
     (not the UI thread). */
    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    // Calls DAO to remove a task from the DB.
    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    // Updates the task in the DB.
    suspend fun update(task: Task) {
        taskDao.updateTask((task))
    }
}
