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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.pluralStringResource
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.compose.ui.platform.LocalContext

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

// Funkcja pomocnicza do zmiany języka aplikacji
fun setAppLanguage(context: Context, languageTag: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val localeManager = context.getSystemService(LocaleManager::class.java)
        localeManager?.applicationLocales = if (languageTag.isEmpty()) {
            LocaleList.getEmptyLocaleList() // Ustawienie systemowe
        } else {
            LocaleList.forLanguageTags(languageTag)
        }
    } else {
        AppCompatDelegate.setApplicationLocales(
            if (languageTag.isEmpty()) LocaleListCompat.getEmptyLocaleList()
            else LocaleListCompat.forLanguageTags(languageTag)
        )
    }
}

@Composable
fun JokeScreen() {
    val context = LocalContext.current
    val jokes = stringArrayResource(R.array.jokes)
    var currentJokeIndex by rememberSaveable { mutableIntStateOf(0) }
    var jokeCount by rememberSaveable { mutableIntStateOf(0) }

    val currentJoke = jokes.getOrElse(currentJokeIndex) { jokes.random() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 1. Pasek przełączania języka na samej górze
        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(onClick = { setAppLanguage(context, "") }) {
                Text(stringResource(R.string.lang_system))
            }
            OutlinedButton(onClick = { setAppLanguage(context, "pl") }) {
                Text(stringResource(R.string.lang_pl))
            }
            OutlinedButton(onClick = { setAppLanguage(context, "en") }) {
                Text(stringResource(R.string.lang_en))
            }
        }

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineMedium
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
                currentJokeIndex = (0 until jokes.size).random()
                jokeCount++
            },
            shape = CircleShape,
            modifier = Modifier.size(140.dp)
        ) {
            Text(
                text = stringResource(R.string.btn_show_joke),
                textAlign = TextAlign.Center
            )
        }

        Text(
            text = pluralStringResource(R.plurals.jokes_count, jokeCount, jokeCount),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}