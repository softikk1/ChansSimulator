package chans.command

import command.Command
import command.Invoker
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

// Реализация Invoker-а.
object ChansInvoker : Invoker<ChansCommands> {
    private val commands = LinkedBlockingQueue<Command>()

    init {
        thread {
            while (true) {
                val command = commands.take()
                command.execute()
            }
        }
    }

    override fun addCommand(command: ChansCommands) {
        commands.add(command)
    }
}