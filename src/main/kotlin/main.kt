import chans.ChanModel
import chans.ChansRepository
import chans.RegistrationModel
import chans.observers.BestChanDisplay
import chans.observers.ChanDisplay
import kotlin.random.Random

val registrationModel = RegistrationModel(
    username = "softikk", password = "qwerty"
)

fun main() {
    val chanDisplay = ChanDisplay().apply { show() }
    val bestChanDisplay = BestChanDisplay().apply { show() }

    val chansRepository = ChansRepository.getInstance(registrationModel)
    while (true) {
        print(Commands.entries.joinToString(", ") {
            it.text
        } + ": ")
        val command = Commands.entries[readln().toInt()]
        when (command) {
            Commands.EXIT -> {
                chanDisplay.jFrame.dispose()
                bestChanDisplay.jFrame.dispose()
                break
            }
            Commands.ADD_CHAN -> {
                print("Enter chan name: ")
                val chanName = readln()
                chansRepository.addChan(
                    ChanModel(
                        id = (chansRepository.chans.lastOrNull()?.id ?: 0) + 1,
                        name = chanName,
                        age = (15..20).random(),
                        height = (150..200).random(),
                        tsundere = Random.nextBoolean()
                    )
                )
            }

            Commands.DEL_CHAN -> {
                print("Enter chan id: ")
                val chanId = readln().toLong()
                chansRepository.deleteChan(chanId)
            }
        }
    }
}