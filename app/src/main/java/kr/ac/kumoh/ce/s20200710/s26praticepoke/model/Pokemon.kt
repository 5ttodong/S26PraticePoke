package kr.ac.kumoh.ce.s20200710.s26praticepoke.model

data class Pokemon(
    val id: Int,
    val name: String,
    val type: String,
    val info: String,
    val image: String,

    //추가
    val height: Double =0.0,
    val weight: Double= 0.0,

    //ai - 여러개의 객체를 담는 목록
    val stats: List<PokemonStat> = emptyList(),
    val abilities: List<PokemonAbility> = emptyList()
)

// 한마리에 여러 능력치와 특성을 저장하는 구조
data class PokemonStat(
    val name: String,
    val value: Int
)

data class PokemonAbility(
    val name: String,
    val isHidden: Boolean
)

// Pokemon(25, "피카츄", "전기", "뺨에 전기 주머니가 있다.")