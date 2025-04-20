package com.example.substracker

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query(value="select * from task order by id ASC")
    fun getAllTasks(): LiveData<List<Task>>
}

/*
insertTask()	Add a new task	Ignores conflict if task already exists
deleteTask()	Remove an existing task	Uses Room’s auto-generated query
updateTask()	Modify an existing task	Also uses Room’s auto-generated query
getAllTasks()	Fetch all tasks as LiveData	Automatically updates UI on DB changes
This interface is what your Repository uses to talk to the database.
Room handles all the SQL under the hood! */
}
}

