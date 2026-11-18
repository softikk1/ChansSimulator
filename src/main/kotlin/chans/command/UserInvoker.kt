package chans.command

import command.Command
import command.Invoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

// Реализация Invoker-а.
object UserInvoker : Invoker {
    private val commands = LinkedBlockingQueue<Command>()
    init {
        thread {
            while (true) {
                val command = commands.take()
                command.execute()
            }
        }
    }
    override fun addCommand(command: Command) {
        commands.add(command)
    }
}