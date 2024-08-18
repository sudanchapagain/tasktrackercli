package src.main.kotlin

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

@Serializable
data class Task(val id: Int, var description: String, var status: String = "todo")

val filePath = "task_tracker_cli.json"
val file = File(filePath)

fun main(args: Array<String>) {
    if (args.isEmpty() || args.size < 2) {
        println("Usage: tasktrackercli <command> [<arguments>]")
        println(
            "commands:\n" +
                    "\tadd (task),\n" +
                    "\tupdate(id, task),\n" +
                    "\tdelete(id),\n" +
                    "\tmark-in-progress(id),\n" +
                    "\tmark-done(id),\n" +
                    "\tlist(done, todo, in-progress"
        )
        return
    }

    checkFile()

    when (args[0]) {
        "add" -> {
            addTask(args[1])
        }

        "update" -> {
            if (args.size < 3) {
                println("update requires two arguments")
                println("Usage: tasktrackercli update <id> [<arguments>]")
                return
            }
            updateTask(args[1].toInt(), args[2])
        }

        "delete" -> {
            deleteTask(args[1].toInt())
        }



        else -> println("Unknown command: ${args[0]}")
    }
}

fun checkFile() {
    if (!file.exists()) {
        try {
            file.createNewFile()
            file.writeText("[]")
        } catch (e: IOException) {
            println("Error creating or writing to file: ${e.message}")
        }
    }
}


fun addTask(taskDescription: String) {
    try {
        val jsonString = file.readText()
        // deserialize JSON string to instance of Muteable list where each object is named Task.
        val tasks = Json.decodeFromString<MutableList<Task>>(jsonString)

        val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
        val newTask = Task(id = newId, description = taskDescription)
        tasks.add(newTask)

        file.writeText(Json.encodeToString(tasks))

        println("Task added successfully (ID: $newId)")
    } catch (e: IOException) {
        println("Error reading or writing to file: ${e.message}")
    }
}

fun updateTask(taskID: Int, taskDescription: String) {
    try {
        val jsonString = file.readText()
        val tasks = Json.decodeFromString<MutableList<Task>>(jsonString)

        val task = tasks.find { it.id == taskID }

        if (task != null) {
            task.description = taskDescription
            file.writeText(Json.encodeToString(tasks))
            println("Task updated successfully (ID: $taskID)")
        } else {
            println("Task not found: $taskID")
        }
    } catch (e: IOException) {
        println("Error reading or writing to file: ${e.message}")
    }
}

fun deleteTask(id: Int) {
    try {
        val jsonString = file.readText()
        val tasks = Json.decodeFromString<MutableList<Task>>(jsonString)

        val task = tasks.find { it.id == id }

        if (task != null) {
            tasks.remove(task)
            file.writeText(Json.encodeToString(tasks))
            println("Task deleted successfully (ID: $id)")
        } else {
            println("Task not found: $id")
        }
    } catch (e: IOException) {
        println("Error reading or writing to file: ${e.message}")
    }
}

