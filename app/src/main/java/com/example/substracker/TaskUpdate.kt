package com.example.substracker

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import java.util.*

class TaskUpdate : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    // Date & time variables
    private var currentDay = 0
    private var currentMonth = 0
    private var currentYear = 0
    private var currentHour = 0
    private var currentMinute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    private lateinit var taskViewModel: TaskViewModel

    // UI components
    private lateinit var updateTaskTitleBox: EditText
    private lateinit var updateDescTextBox: EditText
    private lateinit var updateDateTextBox: TextView
    private lateinit var updateTimeTextBox: TextView
    private lateinit var updateDateButton: Button
    private lateinit var updateTimeButton: Button
    private lateinit var updateTaskButton: Button
    private lateinit var deleteTaskButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_update)

        // 🔧 Link UI views using findViewById
        updateTaskTitleBox = findViewById(R.id.updateTaskTitleBox)
        updateDescTextBox = findViewById(R.id.updateDescTextBox)
        updateDateTextBox = findViewById(R.id.updateDateTextBox)
        updateTimeTextBox = findViewById(R.id.updateTimeTextBox)
        updateDateButton = findViewById(R.id.updateDateButton)
        updateTimeButton = findViewById(R.id.updateTimeButton)
        updateTaskButton = findViewById(R.id.updateTaskButton)
        deleteTaskButton = findViewById(R.id.deleteTaskButton)

        // 📦 Retrieve task from intent
        val task = intent.getParcelableExtra<Task>("extra_item") ?: return

        // Populate views with task data
        updateTaskTitleBox.setText(task.title)
        updateDescTextBox.setText(task.desc)
        updateDateTextBox.text = task.date
        updateTimeTextBox.text = task.time

        // 🗓 Show DatePicker on button click
        updateDateButton.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(this, this, currentYear, currentMonth, currentDay).show()
        }

        // 🕒 Show TimePicker on button click
        updateTimeButton.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(this, this, currentHour, currentMinute, false).show()
        }

        // 🔁 ViewModel setup
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // ✅ Update task
        updateTaskButton.setOnClickListener {
            var flag = true

            if (updateTaskTitleBox.text.isEmpty()) {
                showToast("Please enter a valid title!")
                flag = false
            }
            if (updateDateTextBox.text.isEmpty()) {
                showToast("Please enter a valid date!")
                flag = false
            }
            if (updateTimeTextBox.text.isEmpty()) {
                showToast("Please enter a valid time!")
                flag = false
            }

            if (flag) {
                val updatedTask = Task(
                    id = task.id,
                    title = updateTaskTitleBox.text.toString(),
                    desc = updateDescTextBox.text.ifEmpty { "No Description" }.toString(),
                    date = updateDateTextBox.text.toString(),
                    time = updateTimeTextBox.text.toString()
                )
                taskViewModel.update(updatedTask)
                showToast("Task successfully updated!")
                finish()
            }
        }

        // 🗑 Delete task with confirmation
        deleteTaskButton.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("YES") { _, _ ->
                    taskViewModel.delete(task)
                    showToast("Task deleted successfully!")
                    finish()
                }
                .setNegativeButton("NO", null)
                .show()
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to go back?")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        currentDay = cal.get(Calendar.DAY_OF_MONTH)
        currentMonth = cal.get(Calendar.MONTH)
        currentYear = cal.get(Calendar.YEAR)
    }

    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        currentHour = cal.get(Calendar.HOUR_OF_DAY)
        currentMinute = cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        val isValidDate = when {
            savedYear < currentYear -> false
            savedYear == currentYear && savedMonth < currentMonth -> false
            savedYear == currentYear && savedMonth == currentMonth && savedDay < currentDay -> false
            else -> true
        }

        if (isValidDate) {
            val displayDay = savedDay.toString().padStart(2, '0')
            val displayMonth = savedMonth.toString().padStart(2, '0')
            updateDateTextBox.text = "$displayDay / $displayMonth / $savedYear"
        } else {
            showToast("Please enter a valid date!")
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        val status = if (savedHour >= 12) "PM" else "AM"
        val hour12 = if (savedHour > 12) savedHour - 12 else savedHour
        val displayHour = hour12.toString().padStart(2, '0')
        val displayMinute = savedMinute.toString().padStart(2, '0')

        updateTimeTextBox.text = "$displayHour:$displayMinute  $status"
    }
}
