package com.example.week1.domain

import java.util.Calendar
import java.util.Date

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String,
    val priority: Priority,
    val dueDate: Date = Calendar.getInstance().time,
    val done: Boolean = false
) {
    enum class Priority {
        LOW,
        MEDIUM,
        HIGH
    }
}
