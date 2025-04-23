package com.example.substracker

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

// RecyclerView Adapter that binds a list of Task objects to the UI
class TaskListAdapter : RecyclerView.Adapter<TaskListAdapter.MyViewHolder>() {

    // Internal list that stores all the Task items
    private var taskList = emptyList<Task>()

    // ViewHolder class holds references to all views in a single item layout
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)             // Task title TextView
        val description = itemView.findViewById<TextView>(R.id.description) // Task description TextView
        val date = itemView.findViewById<TextView>(R.id.date)               // Task date TextView
        val time = itemView.findViewById<TextView>(R.id.time)               // Task time TextView
        val itemLayout = itemView.findViewById<View>(R.id.itemLayout)       // Root layout of the item
    }

    // Called when RecyclerView needs a new ViewHolder (a new row)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Inflate the layout for a single task item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return MyViewHolder(view)
    }

    // Called for each item in the list to bind data to the view
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTask = taskList[position]

        // Set task data to corresponding views
        holder.title.text = currentTask.title
        holder.description.text = currentTask.desc
        holder.date.text = currentTask.date
        holder.time.text = currentTask.time

        // Handle click on a task item
        holder.itemLayout.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, TaskUpdate::class.java) // Navigate to TaskUpdate screen
            intent.putExtra("extra_item", currentTask)           // Pass selected task as extra
            context.startActivity(intent)                        // Start the activity
        }
    }

    // Returns the number of items in the list
    override fun getItemCount(): Int = taskList.size

    // Called externally to update the adapter's data and refresh the UI
    fun setData(task: List<Task>) {
        this.taskList = task
        notifyDataSetChanged() // Rebind the updated list to the RecyclerView
    }
}
