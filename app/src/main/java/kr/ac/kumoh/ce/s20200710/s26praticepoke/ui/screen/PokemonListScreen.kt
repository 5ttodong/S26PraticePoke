package kr.ac.kumoh.ce.s20200710.s26praticepoke.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kr.ac.kumoh.ce.s20200710.s26praticepoke.repository.PokemonRepository
import kr.ac.kumoh.ce.s20200710.s26praticepoke.viewmodel.PokemonListViewModel

@Composable
fun PokemonListScreen(
    repository: PokemonRepository,
    viewModel: PokemonListViewModel = viewModel {
        PokemonListViewModel(repository)
    },
    onNavigateToDetail: (Int) -> Unit,
) {
    //1. 이렇게 하면 list목록 자체가 아니라, StateFlow를 가져온다.
    //val pokemonList = viewModel.pokemonList

    //2.그래서 statflow 안의 목록화면에서 사용하고, 변경될때 화면도 갱신되도록 연결
    val pokemonList by viewModel.pokemonList.collectAsStateWithLifecycle()
    LazyColumn {
        items(pokemonList) { pokemon ->
            Button(
                onClick = {
                    onNavigateToDetail(pokemon.id)
                }
            ) {
                Text(pokemon.name)
            }
        }
    }
}