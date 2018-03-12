package htwdd.chessgame.client.util

import kotlin.jvm.Synchronized

open class Observable {
    var changed = false
    var obs: HashSet<Observer> = HashSet()

    // workaround for serialization
    fun initObservable() {
        changed = false
        obs = HashSet()
    }

    @Synchronized
    fun addObserver(o: Observer?) {
        if (o == null)
            throw NullPointerException()
        if (!obs.contains(o)) {
            obs.add(o)
        }
    }

    @Synchronized
    fun deleteObserver(o: Observer) {
        obs.remove(o)
    }

    fun notifyObservers(arg: Any? = null) {

        var arrLocal: Array<Observer> = emptyArray()

        synchronized(this) {
            if (!changed)
                return
            arrLocal = obs.toTypedArray()
            clearChanged()
        }

        for (i in arrLocal.indices.reversed())
            arrLocal[i].update(this, arg)
    }

    @Synchronized
    fun deleteObservers() {
        obs.clear()
    }

    @Synchronized
    protected fun setChanged() {
        changed = true
    }

    @Synchronized
    protected fun clearChanged() {
        changed = false
    }

    @Synchronized
    fun hasChanged(): Boolean {
        return changed
    }

    @Synchronized
    fun countObservers(): Int {
        return obs.size
    }
}

