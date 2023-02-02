package sk.kapitan.NBAstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
                    val team = viewModel.team.observeAsState()
                    Scaffold(
                        topBar = {
                            TopAppBar { 
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = null)
                                }
                                Text(text = team.value?.name ?: "")
                            }
                        }
                    ) {
                        // Screen content
                    }
                }
            }
        }
    }
}