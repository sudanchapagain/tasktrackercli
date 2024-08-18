package src.main.kotlin

import java.io.File
import java.io.IOException

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
}

fun checkFile() {
    val filePath = "task_tracker_cli.json"
    val file = File(filePath)

    if (!file.exists()) {
        try {
            file.createNewFile()
            file.writeText("[]")
        } catch (e: IOException) {
            println("Error creating or writing to file: ${e.message}")
        }
    }
}
