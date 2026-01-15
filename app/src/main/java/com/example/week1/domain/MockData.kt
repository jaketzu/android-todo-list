package com.example.week1.domain

import java.util.Calendar


fun mockData(): List<Task> {
    val dueDate = Calendar.getInstance()
    dueDate.set(Calendar.YEAR, 2026)
    dueDate.set(Calendar.MONTH, Calendar.JANUARY)
    dueDate.set(Calendar.DAY_OF_MONTH, 15)
    dueDate.set(Calendar.HOUR_OF_DAY, 19)
    dueDate.set(Calendar.MINUTE, 0)
    dueDate.set(Calendar.SECOND, 0)

    val tasks: MutableList<Task> = mutableListOf()

    tasks.add(
        Task(
            0,
            "Rakenna sovellus loppuun",
            "Tuota Android sovellus käyttäen Android Studiota IDEnä ja kielenä Kotlinia.",
            Task.Priority.HIGH,
            dueDate.time
        )
    )

    dueDate.set(Calendar.DAY_OF_MONTH, 15)
    dueDate.set(Calendar.HOUR_OF_DAY, 20)

    tasks.add(
        Task(
            1,
            "Lataa projekti Githubiin",
            "Puske projekti Githubiin käyttäen Gittiä ja rakenna sovellus, jonka jälkeen tee uusi release sovellukselle.",
            Task.Priority.MEDIUM,
            dueDate.time
        )
    )

    dueDate.set(Calendar.DAY_OF_MONTH, 15)
    dueDate.set(Calendar.HOUR_OF_DAY, 21)

    tasks.add(
        Task(
            2,
            "Kuvaa esitysvideo",
            "Kuvaa lyhyt esittelyvideo, jossa selität sovelluksen toiminnan vertaisarvioijalle.",
            Task.Priority.MEDIUM,
            dueDate.time
        )
    )

    dueDate.set(Calendar.DAY_OF_MONTH, 16)
    dueDate.set(Calendar.HOUR_OF_DAY, 2)

    tasks.add(
        Task(
            3,
            "Nuku",
            "Krooh pyyh zZZzz.",
            Task.Priority.LOW,
            dueDate.time
        )
    )

    return tasks
}