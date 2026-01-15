package com.example.week1.domain

fun addTask(list: List<Task>, task: Task): List<Task> {
    return list + task
}

fun toggleDone(list: List<Task>, id: Int): List<Task> {
    return list.map { task ->
        if (task.id == id) {
            task.copy(done = !task.done)
        } else {
            task
        }
    }
}

fun filterByDone(list: List<Task>, type: Int): List<Task> {
    when (type) {
        1 -> return list.filter { !it.done }
        2 -> return list.filter { it.done }
    }
    return list
}

fun sortByDueDate(list: List<Task>, direction: Boolean): List<Task> {
    return if (!direction) list.sortedBy { it.dueDate } else list.sortedByDescending { it.dueDate.time }
}