package apps.mjn.remote.failure

sealed class Failure(override val message: String) : Throwable(message) {
    class ConnectionError(message: String) : Failure(message)
    class ServerError(message: String) : Failure(message)
    class NotFoundError(message: String) : Failure(message)
}