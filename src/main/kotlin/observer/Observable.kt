package observer

// Абстракция наблюдаемого объекта.
interface Observable<T> {
    val observers: List<Observer<T>>
    fun notifyObservers()
    fun registerObserver(observer: Observer<T>)
    fun unRegisterObserver(observer: Observer<T>)
}