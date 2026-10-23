package kr.ac.kumoh.ce.s20200710.s26praticepoke.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.ac.kumoh.ce.s20200710.s26praticepoke.repository.PokemonRepository
import kr.ac.kumoh.ce.s20200710.s26praticepoke.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    repository: PokemonRepository,
    viewModel: PokemonDetailViewModel = viewModel {
        PokemonDetailViewModel(
            repository = repository,
            pokemonId = pokemonId
        )
    }
){
//    //viewmodel를 만든후, 검색함수를 호출할때 id 전달
//    val pokemon = viewModel.getPokemonById(pokemonId)
//
//    //포켓몬이 있을때만 설명을 표시
//    pokemon?.let { Text(it.info)}

    val pokemon by viewModel.pokemon.collectAsStateWithLifecycle()

    Text(pokemon.name)
}