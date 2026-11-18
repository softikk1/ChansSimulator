package command

// Invoker (инициатор) - отвечает за запуск действия в определенный момент.
interface Invoker {
    fun addCommand(command: Command)
}