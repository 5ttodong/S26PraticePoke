package kr.ac.kumoh.ce.s20200710.s26praticepoke.model

data class Pokemon(
    val id: Int,
    val name: String,
    val type: String,
    val info: String,
)

// Pokemon(25, "피카츄", "전기", "뺨에 전기 주머니가 있다.")