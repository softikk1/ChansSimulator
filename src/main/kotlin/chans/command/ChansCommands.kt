package chans.command

import chans.ChanModel
import chans.ChansRepository
import command.Command

sealed interface ChansCommands : Command {
    data class CreateChan(
        val repository: ChansRepository, val chanModel: ChanModel
    ) : ChansCommands {
        override fun execute() {
            repository.addChan(chanModel)
        }
    }

    data class DeleteChan(
        val repository: ChansRepository, val chanId: Long
    ) : ChansCommands {
        override fun execute() {
            repository.deleteChan(chanId)
        }
    }
}