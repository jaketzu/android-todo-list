package com.example.week1.domain

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.week1.R
import com.example.week1.ui.theme.Week1Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_home_screen2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setContent {
            Week1Theme {
                var taskTitle by remember { mutableStateOf("") }
                var taskDescription by remember { mutableStateOf("") }

                val priorityValues = Task.Priority.entries
                var taskPriority by remember { mutableStateOf(Task.Priority.LOW) }
                var priorityIndex by remember { mutableIntStateOf(0) }
                var isDropdownExpanded by remember { mutableStateOf(false) }

                var tasks by remember { mutableStateOf(sortByDueDate(mockData(), false)) }
                var visibleTasks by remember { mutableStateOf(sortByDueDate(mockData(), false)) }

                var doneType by remember { mutableIntStateOf(0) }
                var sortType by remember { mutableStateOf(false) }

                val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.UK)

                Scaffold(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.safeDrawing)
                        .fillMaxSize()
                ) { innerPadding ->
                    Column(
                        Modifier
                            .consumeWindowInsets(innerPadding)
                            .padding(12.dp),
                    ) {
                        Text(
                            "Tasks", modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                OutlinedTextField(
                                    value = taskTitle,
                                    onValueChange = { taskTitle = it },
                                    label = { Text("Task title") },
                                    placeholder = { Text("Title") },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(0.6f)
                                )

                                OutlinedTextField(
                                    value = taskDescription,
                                    onValueChange = { taskDescription = it },
                                    label = { Text("Task description") },
                                    placeholder = { Text("Description") },
                                    maxLines = 4,
                                    modifier = Modifier.fillMaxWidth(0.6f)
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.14f)
                                    .border(1.dp, Color.Gray, OutlinedTextFieldDefaults.shape)
                                    .clickable {
                                        isDropdownExpanded = true
                                    },
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${priorityValues[priorityIndex]} ▽")
                                DropdownMenu(
                                    expanded = isDropdownExpanded,
                                    onDismissRequest = {
                                        isDropdownExpanded = false
                                    }) {

                                    priorityValues.forEachIndexed { index, priority ->
                                        DropdownMenuItem(
                                            text = {
                                                Text("$priority")
                                            },
                                            onClick = {
                                                isDropdownExpanded = false
                                                priorityIndex = index
                                                taskPriority = priority
                                            })
                                    }
                                }
                            }
                        }

                        Spacer(Modifier.height(4.dp))

                        Button(modifier = Modifier.fillMaxWidth(), onClick = {
                            if (taskTitle.trim() != "" && taskDescription.trim() != "") {
                                val taskDueDate = Calendar.getInstance()
                                taskDueDate.add(Calendar.DAY_OF_MONTH, 1)

                                val task = Task(
                                    tasks.size,
                                    taskTitle,
                                    taskDescription,
                                    taskPriority,
                                    taskDueDate.time
                                )

                                tasks = addTask(tasks, task)
                                visibleTasks =
                                    sortByDueDate(filterByDone(tasks, doneType), sortType)

                                taskTitle = ""
                                taskDescription = ""
                            }
                        }) { Text("Add task") }

                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(onClick = {
                                doneType = if (doneType == 2) 0 else doneType + 1
                                visibleTasks =
                                    sortByDueDate(filterByDone(tasks, doneType), sortType)

                            }, Modifier.weight(1f)) { Text("Filter by done") }

                            Button(onClick = {
                                sortType = !sortType
                                visibleTasks =
                                    sortByDueDate(filterByDone(tasks, doneType), sortType)
                            }, Modifier.weight(1f)) { Text("Sort by due date") }
                        }

                        Spacer(Modifier.height(12.dp))

                        Column(
                            Modifier.verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            for (task in visibleTasks) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        Modifier.fillMaxWidth(0.75f),
                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(task.title)
                                        Text("${task.priority}    ${formatter.format(task.dueDate.time)}")
                                        Text(task.description)
                                    }

                                    Button(onClick = {
                                        tasks = toggleDone(
                                            tasks,
                                            task.id
                                        )

                                        visibleTasks =
                                            sortByDueDate(filterByDone(tasks, doneType), sortType)
                                    }) {
                                        Text(if (task.done) "❎" else "✅")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
