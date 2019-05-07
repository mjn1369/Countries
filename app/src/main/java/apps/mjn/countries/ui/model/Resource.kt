package apps.mjn.countries.ui.model

data class Resource<out T>(val resourceState: ResourceState, val data: T? = null, val throwable: Throwable? = null)