package com.uvg.lab_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Modelo para el Pokémon (simplificado)
data class Pokemon(val name: String, val url: String)

// Definir la interfaz de PokeAPI
interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int): PokeResponse
}

data class PokeResponse(val results: List<Pokemon>)

// Configurar Retrofit
object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: PokeApiService = retrofit.create(PokeApiService::class.java)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen() {
    val pokemonList = remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val response = RetrofitClient.apiService.getPokemonList(100)
            pokemonList.value = response.results
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pokémon List") }
            )
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(pokemonList.value) { pokemon ->
                PokemonItem(pokemon)
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${pokemon.url.split("/").filter { it.isNotEmpty() }.last()}.png"
            ),
            contentDescription = pokemon.name,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = pokemon.name)
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListScreenPreview() {
    PokemonListScreen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonListScreen()
        }
    }
}
