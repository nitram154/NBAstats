package sk.kapitan.NBAstats

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import sk.kapitan.NBAstats.ui.theme.NBAstatsTheme
import java.text.ParseException
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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
                    val games = viewModel.games.observeAsState()
                    Scaffold(
                        topBar = {
                            TopAppBar { 
                                IconButton(onClick = { finish() }) {
                                    Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = null)
                                }
                                Text(text = team.value?.name ?: "")
                            }
                        }
                    ) {
                        LazyColumn(content = {
                            itemsIndexed(games.value?: emptyList()){  index, item ->
                                Column() {
                                    Row(modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth()){

                                        Text(text = "${parseDate(item.date) } - ${item.homeTeam.name}  ${item.homeTeamScore} : ${item.visitorTeamScore} ${item.visitorTeam.name}")

                                    }
                                    if (index < games.value?.lastIndex ?: 0)
                                        Divider(color = Color.Black, thickness = 1.dp)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
    fun parseDate(
        inputDateString: String?,
        inputDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'", Locale.US),
        outputDateFormat: SimpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.US)
    ): String? {
        var date: Date? = null
        var outputDateString: String? = null
        try {
            date = inputDateFormat.parse(inputDateString)
            outputDateString = outputDateFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateString
    }
}