package com.example.applicationtranslation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.pluralStringResource


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JokeScreen()
                }
            }
        }
    }
}

@Composable
fun JokeScreen() {
    val jokes = stringArrayResource(R.array.jokes)
    var currentJoke by rememberSaveable { mutableStateOf(jokes.random()) }

    // 1. Stan licznika kliknięć
    var jokeCount by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = currentJoke,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(24.dp)
            )
        }

        Button(
            onClick = {
                currentJoke = jokes.random()
                jokeCount++ // 2. Zwiększamy licznik przy każdym kliknięciu
            },
            shape = CircleShape,
            modifier = Modifier.size(140.dp)
        ) {
            Text(
                text = stringResource(R.string.btn_show_joke),
                textAlign = TextAlign.Center
            )
        }

        // 3. Pobranie odpowiedniej formy z plurals
        // UWAGA: Liczbę przekazujemy DWA RAZY (raz dla formy, raz jako wartość %1$d)
        Text(
            text = pluralStringResource(R.plurals.jokes_count, jokeCount, jokeCount),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}