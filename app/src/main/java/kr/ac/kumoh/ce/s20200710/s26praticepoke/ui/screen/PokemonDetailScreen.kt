package kr.ac.kumoh.ce.s20200710.s26praticepoke.ui.screen

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
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

    Column(
        //내가 한거
        //포켓몬리스트스크린에 있는 비동기 사진 활용
        //상세화면에 해당포켓몬의 사진이 나오게 설정
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        // 수직으로 정렬
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        AsyncImage(
            model = pokemon.image,
            contentDescription = "${pokemon.name} 이미지",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(240.dp)
                .padding(8.dp)
                //.clip(CircleShape)
                .clip(RoundedCornerShape(percent = 10))
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        Text(text = pokemon.name,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,)
        //포켓몬 타입도 출력
        Text(text = pokemon.type,
            fontWeight = FontWeight.Bold)
        Spacer(Modifier.width(16.dp))
        Text(pokemon.info)

        Spacer(modifier = Modifier.height(24.dp))

        // 신체 정보
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "신체 정보",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("키")
                        Text(
                            text = "${pokemon.height} m",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("몸무게")
                        Text(
                            text = "${pokemon.weight} kg",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ai 도움
        // 기본 능력치
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "기본 능력치",
                    style = MaterialTheme.typography.titleMedium
                )

                pokemon.stats.forEach { stat ->
                    val label = when (stat.name) {
                        "hp" -> "HP"
                        "attack" -> "공격"
                        "defense" -> "방어"
                        "special-attack" -> "특수공격"
                        "special-defense" -> "특수방어"
                        "speed" -> "스피드"
                        else -> stat.name
                    }

                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(label)
                            Text(stat.value.toString())
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        // 시각적 비교를 위해 255를 기준으로 표시
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(
                                        (stat.value / 255f).coerceIn(0f, 1f)
                                    )
                                    .fillMaxHeight()
                                    .background(
                                        MaterialTheme.colorScheme.primary
                                    )
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 특성
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "특성",
                    style = MaterialTheme.typography.titleMedium
                )

                pokemon.abilities.forEach { ability ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ability.name,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            shape = RoundedCornerShape(50),
                            color = if (ability.isHidden) {
                                MaterialTheme.colorScheme.tertiaryContainer
                            } else {
                                MaterialTheme.colorScheme.secondaryContainer
                            }
                        ) {
                            Text(
                                text = if (ability.isHidden) {
                                    "숨겨진 특성"
                                } else {
                                    "일반 특성"
                                },
                                modifier = Modifier.padding(
                                    horizontal = 10.dp,
                                    vertical = 4.dp
                                ),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}