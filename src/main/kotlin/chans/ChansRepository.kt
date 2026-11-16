package chans

import kotlinx.serialization.json.Json
import observer.MutableObservable
import java.io.File

// Реализация паттерна Singleton.
// Репозиторий для взаимодействия наблюдателя и наблюдаемого объекта.
class ChansRepository private constructor() {
//    init {
//        println("Create chan repo.")
//    }

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
    val mutableObservableChans = MutableObservable(
        initialValue = chans
    )
    val mutableObservableBestChan = MutableObservable(
        initialValue = chans.singleOrNull { it.name == "Angelica" })

    fun addChan(chanModel: ChanModel) {
        file.writeText(Json.encodeToString(chans + chanModel))
        // Точка взаимодействия наблюдаемого объекта и наблюдателя.
        mutableObservableChans.currentValue = chans
        mutableObservableBestChan.currentValue = chans.singleOrNull { it.name == "Angelica" }
    }

    fun deleteChan(chanId: Long) {
        file.writeText(Json.encodeToString(chans.filter { it.id != chanId }))
        // Точка взаимодействия наблюдаемого объекта и наблюдателя.
        mutableObservableChans.currentValue = chans
        mutableObservableBestChan.currentValue = chans.singleOrNull { it.name == "Angelica" }
    }
}