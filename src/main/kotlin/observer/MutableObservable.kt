package observer

// Реализация наблюдаемого объекта.
class MutableObservable<T>(initialValue: T) : Observable<T> {
    var currentValue: T = initialValue
        set(value) {
            field = value
            // Точка взаимодействия наблюдаемого объекта и наблюдателя.
            notifyObservers()
        }

    override val observers: List<Observer<T>>
        field = mutableListOf()

    override fun notifyObservers() {
        observers.forEach {
            // Точка взаимодействия наблюдаемого объекта и наблюдателя.
            it.onChange(currentValue) // Метод наблюдателя.
        }
    }

    override fun registerObserver(observer: Observer<T>) {
        observers.addLast(observer)
        notifyObservers()
    }

    override fun unRegisterObserver(observer: Observer<T>) {
        observers.remove(observer)
    }
}