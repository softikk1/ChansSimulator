package chans

import chans.command.UserInvoker
import command.Command
import kotlinx.serialization.json.Json
import observer.MutableObservable
import observer.Observable
import java.io.File
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

// Реализация паттерна Singleton.
// Репозиторий для взаимодействия наблюдателя и наблюдаемого объекта.
class ChansRepository private constructor() {
    init {
        println("Create chan repo.")
    }

    companion object {
        private var instance: ChansRepository? = null
        private var registrationModel: RegistrationModel? = null
        private val lock = Any() // Mutex

        fun getInstance(registrationModel: RegistrationModel): ChansRepository {
            if (registrationModel.username.isBlank()) throw IllegalArgumentException("Username is blank.")
            if (registrationModel.password.isBlank()) throw IllegalArgumentException("Password is blank.")

            if (this.registrationModel == null) this.registrationModel =
                RegistrationModel(registrationModel.username, registrationModel.password)
            this.registrationModel?.let {
                if (registrationModel.username != it.username || registrationModel.password != it.password) throw Exception(
                    "Registration error."
                )
            }

            // Блок синхронизации.
            synchronized(lock) {
                if (instance == null) instance = ChansRepository()
            }
            return instance!!
        }
    }

    val file = File("src/main/resources/chans.json")

    init {
        if (!file.exists()) {
            file.createNewFile()
            file.writeText("[]")
        }
    }

    val chans
        get() = Json.decodeFromString<List<ChanModel>>(file.readText().trim())

    // Наблюдаемые объекты.
    val mutableObservableChans: Observable<List<ChanModel>>
        field = MutableObservable(
            initialValue = chans
        )

    val mutableObservableBestChan: Observable<ChanModel?>
        field = MutableObservable(
            initialValue = chans.singleOrNull { it.name == "Angelica" })

    fun addChan(chanModel: ChanModel) {
        UserInvoker.addCommand {
            file.writeText(Json.encodeToString(chans + chanModel))
            Thread.sleep(5_000) // Имитация тяжелого процесса.
            // Точка взаимодействия наблюдаемого объекта и наблюдателя.
            mutableObservableChans.currentValue = chans
            mutableObservableBestChan.currentValue = chans.singleOrNull { it.name == "Angelica" }
        }
    }

    fun deleteChan(chanId: Long) {
        UserInvoker.addCommand {
            Thread.sleep(5_000) // Имитация тяжелого процесса.
            file.writeText(Json.encodeToString(chans.filter { it.id != chanId }))
            // Точка взаимодействия наблюдаемого объекта и наблюдателя.
            mutableObservableChans.currentValue = chans
            mutableObservableBestChan.currentValue = chans.singleOrNull { it.name == "Angelica" }
        }
    }
}