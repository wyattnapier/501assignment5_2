package com.example.assign5_2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Task(
    val id: Int,
    val label: String,
    val isDone: Boolean = false
)

class TasksViewModel : ViewModel() {
    var tasks by mutableStateOf(
        listOf(
            Task(1, "Buy groceries"),
            Task(2, "Study Compose navigation"),
            Task(3, "Go to the gym")
        )
    )
        private set

    fun toggleTask(id: Int) {
        tasks = tasks.map {
            if (it.id == id) it.copy(isDone = !it.isDone) else it
        }
    }

    fun addTask(label: String) {
        tasks = tasks + Task(id = tasks.size + 1, label = label)
    }
}