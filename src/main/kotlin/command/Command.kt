package command

// Executer - отвечает за само выполнение действия.
fun interface Command {
    fun execute()
}