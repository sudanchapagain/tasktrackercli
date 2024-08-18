package src.main.kotlin

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
}
