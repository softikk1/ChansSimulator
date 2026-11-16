package observer

// Реализация наблюдаемого объекта.
class MutableObservable<T>(initialValue: T) : Observable<T> {
    var currentValue: T = initialValue
        set(value) {
            field = value
            // Точка взаимодействия наблюдаемого объекта и наблюдателя.
            notifyObservers()
        }

    private val _observers: MutableList<Observer<T>> = mutableListOf()
    override val observers: List<Observer<T>>
        get() = _observers.toList()

    override fun notifyObservers() {
        _observers.forEach {
            // Точка взаимодействия наблюдаемого объекта и наблюдателя.
            it.onChange(currentValue) // Метод наблюдателя.
        }
    }

    override fun registerObserver(observer: Observer<T>) {
        _observers.addLast(observer)
        notifyObservers()
    }

    override fun unRegisterObserver(observer: Observer<T>) {
        _observers.remove(observer)
    }
}