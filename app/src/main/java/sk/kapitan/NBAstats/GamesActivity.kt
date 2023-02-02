package sk.kapitan.NBAstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import sk.kapitan.NBAstats.ui.theme.NBAstatsTheme

const val GAMES_KEY = "GAMES_KEY"

class GamesActivity : ComponentActivity() {

    private val viewModel by viewModel<GamesViewModel>(parameters = {parametersOf(intent.getIntExtra(GAMES_KEY,1))})
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NBAstatsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    val teams = viewModel.teams.observeAsState()
//                    LazyColumn(content = {
//                        items(teams.value ?: emptyList()) {
//                            Text(text = it.name)
//                        }
//                    })
                }
            }
        }
    }
}