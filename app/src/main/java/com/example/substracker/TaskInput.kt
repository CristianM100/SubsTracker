package com.example.substracker

import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.util.*



class TaskInput : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    // Variables for storing current date and time
    private var currentDay = 0
    private var currentMonth = 0
    private var currentYear = 0
    private var currentHour = 0
    private var currentMinute = 0

    // Variables for storing selected date and time
    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0

    // ViewModel instance for task management
    private lateinit var taskViewModel: TaskViewModel

    // UI components (EditText, TextView, Buttons)
    private lateinit var newTaskTitleBox: EditText
    private lateinit var newDescTextBox: EditText
    private lateinit var newDateTextBox: TextView
    private lateinit var newTimeTextBox: TextView
    private lateinit var newDateButton: Button
    private lateinit var newTimeButton: Button
    private lateinit var createTaskButton: Button

    // Called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_input)

        // Initialize views using findViewById
        newTaskTitleBox = findViewById(R.id.newTaskTitleBox)
        newDescTextBox = findViewById(R.id.newDescTextBox)
        newDateTextBox = findViewById(R.id.newDateTextBox)
        newTimeTextBox = findViewById(R.id.newTimeTextBox)
        newDateButton = findViewById(R.id.newDateButton)
        newTimeButton = findViewById(R.id.newTimeButton)
        createTaskButton = findViewById(R.id.createTaskButton)

        // Set up listeners for the date and time buttons
        newDateButton.setOnClickListener {
            getDateCalendar()
            DatePickerDialog(this, this, currentYear, currentMonth, currentDay).show()
        }

        newTimeButton.setOnClickListener {
            getTimeCalendar()
            TimePickerDialog(this, this, currentHour, currentMinute, false).show()
        }

        // Initialize the ViewModel for managing tasks
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // Set up listener for the "Create Task" button
        createTaskButton.setOnClickListener {
            var flag = true

            // Check if the title, date, and time fields are valid
            if (newTaskTitleBox.text.isEmpty()) {
                showToast("Please enter a valid title!")
                flag = false
            }
            if (newDateTextBox.text.isEmpty()) {
                showToast("Please enter a valid date!")
                flag = false
            }
            if (newTimeTextBox.text.isEmpty()) {
                flag = false
                showToast("Please enter a valid time!")
            }

            // If all fields are valid, save the task and finish the activity
            if (flag) {
                val title = newTaskTitleBox.text.toString()
                var desc = "No Description"
                if (newDescTextBox.text.isNotEmpty()) {
                    desc = newDescTextBox.text.toString()
                }
                val date = newDateTextBox.text.toString()
                val time = newTimeTextBox.text.toString()

                // Insert the task into the ViewModel
                taskViewModel.insert(Task(0, title, desc, date, time))

                showToast("Task successfully added!")
                finish()
            }
        }
    }

    // Handle back press with a confirmation dialog for unsaved changes
    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("You have unsaved changes. Are you sure you want to go back?")
            .setPositiveButton(android.R.string.ok) { dialog, whichButton ->
                super.onBackPressed()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, whichButton -> }
            .show()
    }

    // Helper function to show a Toast message
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Get the current date from the system calendar
    private fun getDateCalendar() {
        val cal = Calendar.getInstance()
        currentDay = cal.get(Calendar.DAY_OF_MONTH)
        currentMonth = cal.get(Calendar.MONTH)
        currentYear = cal.get(Calendar.YEAR)
    }

    // Get the current time from the system calendar
    private fun getTimeCalendar() {
        val cal = Calendar.getInstance()
        currentHour = cal.get(Calendar.HOUR_OF_DAY)
        currentMinute = cal.get(Calendar.MINUTE)
    }

    // Callback method for handling date selection
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        var flag = 1
        // Validate the selected date to ensure it's not in the past
        if (savedYear < currentYear) flag = -1
        else if (savedYear == currentYear && savedMonth < currentMonth) flag = -1
        else if (savedYear == currentYear && savedMonth == currentMonth && savedDay < currentDay) flag = -1

        // If the date is valid, format and display it
        if (flag == 1) {
            var displayDay = savedDay.toString()
            var displayMonth = savedMonth.toString()

            // Format day and month to always show two digits
            if (savedDay < 10) displayDay = "0$displayDay"
            if (savedMonth < 10) displayMonth = "0$displayMonth"

            newDateTextBox.text = "$displayDay / $displayMonth / $savedYear"
        } else {
            showToast("Please enter a valid date!")
        }
    }

    // Callback method for handling time selection
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        var displayHour: String
        var displayMinute: String
        var status: String

        // Format the hour to AM/PM
        if (savedHour > 12) {
            displayHour = (savedHour - 12).toString()
            status = "PM"
        } else {
            displayHour = savedHour.toString()
            status = "AM"
        }

        // Format minute to always show two digits
        displayMinute = savedMinute.toString()

        if (displayHour.toInt() < 10) displayHour = "0$displayHour"
        if (displayMinute.toInt() < 10) displayMinute = "0$displayMinute"

        // Update the time TextView with the formatted time
        newTimeTextBox.text = "$displayHour:$displayMinute  $status"
    }
}