package kr.ac.kumoh.ce.s20200710.s26praticepoke

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable
import kr.ac.kumoh.ce.s20200710.s26praticepoke.model.Pokemon
import kr.ac.kumoh.ce.s20200710.s26praticepoke.ui.theme.S26PraticePokeTheme
import kr.ac.kumoh.ce.s20200710.s26praticepoke.viewmodel.PokemonDetailViewModel
import kr.ac.kumoh.ce.s20200710.s26praticepoke.viewmodel.PokemonListViewModel
import java.util.Map.entry

sealed interface PokemonRoute : NavKey {
    @Serializable
    data object PokemonList : PokemonRoute

    @Serializable
    data class PokemonDetail(val pokemonId: Int) : PokemonRoute
}

//val pokemons = listOf(
//    Pokemon(1, "이상해씨", "풀/독", "태어날 때부터 등에 식물의 씨앗이 있어 조금씩 자란다."),
//    Pokemon(4, "파이리", "불꽃", "태어날 때부터 꼬리에 불이 붙어 있다. 꺼지면 죽는다."),
//    Pokemon(7, "꼬부기", "물", "등껍질이 단단하여 공격을 잘 막아내고 물을 뿜어낸다."),
//    Pokemon(25, "피카츄", "전기", "뺨에 전기 주머니가 있어서 위기에 처하면 전기를 방출한다.")
//)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            S26PraticePokeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val backStack = rememberNavBackStack(PokemonRoute.PokemonList)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backStack,

            entryDecorators = listOf(
                // Add the default decorators for managing scenes and saving state
                rememberSaveableStateHolderNavEntryDecorator(),
            ),
            entryProvider = entryProvider {
                entry<PokemonRoute.PokemonList> {
                    PokemonListScreen(

                        //viewmodel에서 pokemonlist를 만들어서 필요가 없어짐
                        //pokemonList = pokemons,
                        onNavigateToDetail = { id ->
                            backStack.add(PokemonRoute.PokemonDetail(pokemonId = id))
                        }
                    )
                }
                entry<PokemonRoute.PokemonDetail> { key ->
                    PokemonDetailScreen(key.pokemonId)
                }
            }
        )
    }
}
@Composable
fun PokemonListScreen(
    //pokemonList: List<Pokemon>,
    onNavigateToDetail: (Int) -> Unit,
    viewModel: PokemonListViewModel = viewModel()
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

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    viewModel: PokemonDetailViewModel = viewModel(),
){
    val pokemon = viewModel.getPokemonById(pokemonId)

    //포켓몬이 있을때만 설명을 표시
    pokemon?.let { Text(it.info)}
}