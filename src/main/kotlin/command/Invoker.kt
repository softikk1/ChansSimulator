package command

// Invoker (инициатор) - отвечает за запуск действия в определенный момент.
interface Invoker<T : Command> {
    fun addCommand(command: T)
}