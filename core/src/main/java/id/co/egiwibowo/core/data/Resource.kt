package id.co.egiwibowo.core.data

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading<T>(val data: T? = null) : Resource<T>()
    class Error<T>(
        val throwable: Throwable? = null,
        val  data: T? = null
    ) : Resource<T>()
}

fun <T : Any> Resource<T>.getData(): T? {
    if (this is Resource.Success) return data
    return null
}
