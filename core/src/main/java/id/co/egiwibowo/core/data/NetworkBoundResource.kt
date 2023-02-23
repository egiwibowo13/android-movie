package id.co.egiwibowo.core.data

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDB: () -> Flow<ResultType>,
    crossinline createCall: suspend () -> RequestType,
    crossinline saveCallResult: suspend (data: RequestType) -> Unit,
    crossinline shouldFetch: (data: ResultType?) -> Boolean = { true }
) = flow {
    // First step, fetch data from the local cache
    val data = loadFromDB().first()

    // If shouldFetch returns true,
    val resource = if (shouldFetch(data)) {
        try {
            // make a networking call
            val resultType = createCall()

            // save it to the database
            saveCallResult(resultType)

            // Now fetch data again from the database and Dispatch it to the UI
            loadFromDB()
        } catch (throwable: Throwable) {
            // Dispatch any error emitted to the UI, plus data emmited from the Database
            throw throwable
        }

        // If should fetch returned false
    } else {
        // Make a query to the database and Dispatch it to the UI.
        loadFromDB()
    }

    // Emit the resource variable
    emitAll(resource)
}