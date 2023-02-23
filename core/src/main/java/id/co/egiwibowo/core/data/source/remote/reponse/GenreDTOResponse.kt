package id.co.egiwibowo.core.data.source.remote.reponse

import id.co.egiwibowo.core.domain.models.Genre

data class GenreDTOResponse(
    val id: Int,
    val name: String
)

fun GenreDTOResponse.toDomain(): Genre {
    return Genre(
        id = id,
        name = name
    )
}
