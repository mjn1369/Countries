package apps.mjn.domain.observer

import io.reactivex.observers.DisposableSingleObserver

class SingleObserver<T>(private val success: (t: T) -> Unit, private val error: (e: Throwable) -> Unit) :
    DisposableSingleObserver<T>() {

    override fun onSuccess(t: T) {
        success(t)
        dispose()
    }

    override fun onError(e: Throwable) {
        error(e)
        dispose()
    }
}