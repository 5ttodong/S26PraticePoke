package kr.ac.kumoh.ce.s20200710.s26praticepoke.repository

import kr.ac.kumoh.ce.s20200710.s26praticepoke.model.Pokemon

interface PokemonRepository { //제공할 기능의 규칙
    suspend fun getAllPokemon(): List<Pokemon>
    suspend fun getPokemonById(id: Int): Pokemon?
}