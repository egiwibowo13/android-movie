package id.co.egiwibowo.core.data.source.remote.reponse

data class MoviesDTOResponse(
    val page: Int,
    val results: List<MovieItemDTOResponse>
)