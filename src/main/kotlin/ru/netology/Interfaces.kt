package ru.netology

interface GenericElementInterface {
    public var id: Int;
}

interface GenericServiceInterface<T> {
    fun add(element: T): T
    fun delete(id: Int)
    fun update(element: T): Boolean
    fun read(): List<T>
    fun getById(id: Int): T
    fun restore(id: Int)
    fun clear()
}
