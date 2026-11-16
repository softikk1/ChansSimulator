package observer

// Абстракция наблюдателя.
fun interface Observer<T> {
    fun onChange(value: T)
}