package com.example.substracker
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class  TaskListAdapter: RecyclerView.Adapter<TaskListAdapter.MyViewHolder>() {
    private var taskList = emptyList<Task>() // This holds the list of tasks to display.
                                             // Initially empty, gets filled by setData().
    // This holds the layout view for a single item in the list.
     // It’s a wrapper around the view so you can access it in onBindViewHolder.
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    /*Called when RecyclerView needs to create a new row.
        It inflates the layout from R.layout.item (your item XML file).
        Wraps the view in a MyViewHolder.*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    /*Called for each item in the list to bind data to the view.
       currentTask is the task at the given position.
      Fills in the task's title, description, date, and time in the UI. */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTask = taskList[position]

        holder.itemView.title.text = currentTask.title
        holder.itemView.description.text = currentTask.desc
        holder.itemView.date.text = currentTask.date
        holder.itemView.time.text = currentTask.time

        /* When the user clicks a task item, it starts the TaskUpdate activity.
        The clicked Task is passed using an Intent extra (extra_item).
        This is possible because Task is Parcelable. */
        holder.itemView.itemLayout.setOnClickListener() {
            val context = holder.itemView.context
            val intent = Intent(context,TaskUpdate::class.java)
            intent.putExtra("extra_item",currentTask)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return taskList.size  // Tells the RecyclerView how many items are in the list.

    }

   /* Called from your MainActivity when LiveData changes.
    It updates the internal task list and refreshes the whole RecyclerView. */
    fun setData(task: List<Task>){
        this.taskList = task
        notifyDataSetChanged()
    }
}