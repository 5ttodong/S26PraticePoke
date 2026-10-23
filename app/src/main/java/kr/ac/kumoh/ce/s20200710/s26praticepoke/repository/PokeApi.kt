package kr.ac.kumoh.ce.s20200710.s26praticepoke.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 100
    ): PokemonListDto

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): PokemonDetailDto

    // 한국어 이름과 설명은 종 (species) 정보에 있음
    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(
        @Path("id") id: Int
    ): PokemonSpeciesDto
}

@kotlinx.serialization.Serializable
data class PokemonListDto(
    @SerialName("results") val results: List<ResultDto>
) {
    @kotlinx.serialization.Serializable
    data class ResultDto(
        @SerialName("url") val url: String
    )
}

@kotlinx.serialization.Serializable
data class PokemonDetailDto(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("types") val types: List<TypeSlotDto>,
    @SerialName("sprites") val sprites: SpritesDto,

    //추가
    @SerialName("height") val height: Int,
    @SerialName("weight") val weight: Int,
    // AI 도움: 능력치, 특성을 받는 속성 추가
    @SerialName("stats") val stats: List<StatSlotDto>,
    @SerialName("abilities") val abilities: List<AbilitySlotDto>
) {
    @kotlinx.serialization.Serializable
    data class TypeSlotDto(
        @SerialName("slot") val slot: Int,
        @SerialName("type") val type: TypeDto
    )

    @kotlinx.serialization.Serializable
    data class TypeDto(
        @SerialName("name") val name: String
    )

    @Serializable
    data class SpritesDto(
        @SerialName("front_default") val frontDefault: String? = null
    )
}

@Serializable
data class LanguageDto(
    @SerialName("name") val name: String
)

// AI 도움: 능력치 한 항목의 수치와 이름 정보
@Serializable
data class StatSlotDto(
    @SerialName("base_stat") val baseStat: Int,
    @SerialName("stat") val stat: StatDto
)
// AI 도움: 특성 한 항목의 숨겨진 특성 여부와 이름 정보
@Serializable
data class StatDto(
    @SerialName("name") val name: String
)

// AI 도움: 특성 한 항목의 숨겨진 특성 여부와 이름 정보
@Serializable
data class AbilitySlotDto(
    @SerialName("is_hidden") val isHidden: Boolean,
    @SerialName("ability") val ability: AbilityDto
)
// AI 도움: 특성 한 항목의 숨겨진 특성 여부와 이름 정보
@Serializable
data class AbilityDto(
    @SerialName("name") val name: String
)
@Serializable
data class PokemonSpeciesDto(
    @SerialName("names") val names: List<NameDto> = emptyList(),
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntryDto> = emptyList()
) {
    @Serializable
    data class NameDto(
        @SerialName("name") val name: String,
        @SerialName("language") val language: LanguageDto
    )

    @Serializable
    data class FlavorTextEntryDto(
        @SerialName("flavor_text") val flavorText: String,
        @SerialName("language") val language: LanguageDto
    )
}