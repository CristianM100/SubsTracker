package com.example.substracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        /*val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)*/

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/

        lateinit var taskViewModel: TaskViewModel // Declares a TaskViewModel that will later be initialized.
                                                  //lateinit means it will be initialized after declaration but before use.

        val space = ItemSpace() // Adds spacing between list items.
        val adapter = TaskListAdapter() // Adapter to display tasks in the list.
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView) // UI component to display scrolling list.
        val emptyView = findViewById<TextView>(R.id.emptyView) // A text view shown when there are no tasks.

        recyclerView.addItemDecoration(space)  // Decorates list items (e.g., spacing).
        recyclerView.adapter = adapter // Sets the adapter and layout manager (vertical list).
        adapter.registerAdapterDataObserver(EmptyDataObserver(recyclerView,emptyView)) //Observes data to show or hide the empty view based on whether there are tasks.
        recyclerView.layoutManager = LinearLayoutManager(this)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java) // Initializes the TaskViewModel using ViewModelProvider.
        taskViewModel.allTasks.observe(this,Observer{ task-> // Observes the allTasks LiveData from the ViewModel.
            adapter.setData((task))  // When tasks change, the adapter is updated, and the list refreshes.
        })

        val create = findViewById<FloatingActionButton>(R.id.createTaskButton)

        create.setOnClickListener()
        {
            startActivity(Intent(this,TaskInput::class.java))
        } // When the FloatingActionButton is clicked, it starts the TaskInput activity (a screen for creating a new task).
    }
}

/*      Displays a list of tasks using RecyclerView.

        Connects to TaskViewModel, observing data from Room database via LiveData.

        Updates the UI automatically when task data changes.

        Navigates to a task input screen when the user taps the "Add" button.

        Shows a message (via emptyView) when no tasks exist.
 */