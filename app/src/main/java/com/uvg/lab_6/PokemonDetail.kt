package com.uvg.lab_6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment

class PokemonDetail : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pokemonId = intent.getStringExtra("pokemonId") ?: "1" // Recibir el ID del Pokémon

        setContent {
            PokemonDetailScreen(pokemonId) {
                finish() // Acción para regresar a la lista
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(pokemonId: String, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Fragment") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Row para Front y Back
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Front", style = MaterialTheme.typography.titleMedium)
                    PokemonImage(
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png",
                        contentDescription = "Front"
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Back", style = MaterialTheme.typography.titleMedium)
                    PokemonImage(
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$pokemonId.png",
                        contentDescription = "Back"
                    )
                }
            }

            // Row para Front Shiny y Back Shiny
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Front Shiny", style = MaterialTheme.typography.titleMedium)
                    PokemonImage(
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$pokemonId.png",
                        contentDescription = "Front Shiny"
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Back Shiny", style = MaterialTheme.typography.titleMedium)
                    PokemonImage(
                        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/$pokemonId.png",
                        contentDescription = "Back Shiny"
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonImage(imageUrl: String, contentDescription: String) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = contentDescription,
        modifier = Modifier
            .size(128.dp)
            .padding(8.dp),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreview() {
    PokemonDetailScreen("1") {}
}
