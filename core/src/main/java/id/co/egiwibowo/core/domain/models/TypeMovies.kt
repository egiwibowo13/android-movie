package id.co.egiwibowo.core.domain.models

import java.util.*

//infix inline fun <reified E : Enum<E>, V> ((E) -> V).findBy(value: V): E? {
//    return enumValues<E>().firstOrNull { this(it) == value }
//}

enum class TypeMovies {
    NOW_PLAYING,
    UPCOMING,
    POPULAR,
    TOP_RATED
}

//fun a() {
//    TypeMovies.NOW_PLAYING.name
//    val cardType = TypeMovies.valueOf("name".uppercase(Locale.getDefault()))
//}