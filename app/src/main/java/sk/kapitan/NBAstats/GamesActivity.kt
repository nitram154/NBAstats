package sk.kapitan.NBAstats

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.util.Pair
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import sk.kapitan.NBAstats.data.models.GameData
import sk.kapitan.NBAstats.data.models.TeamData
import sk.kapitan.NBAstats.ui.theme.NBAstatsTheme

const val GAMES_KEY = "GAMES_KEY"

class GamesActivity : AppCompatActivity() {

    private val viewModel by viewModel<GamesViewModel>(parameters = {
        parametersOf(
            intent.getIntExtra(
                GAMES_KEY,
                1
            )
        )
    })

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
                            team.value?.let { AppBar(it) }
                        }
                    ) {
                        games.value?.let { GamesContent(it) } ?: Box {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun AppBar(team: TeamData) {
        TopAppBar(
            backgroundColor = Color(getColor(R.color.orange))
        ) {
            IconButton(onClick = { finish() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null
                )
            }
            Text(text = team.name)
            Box(modifier = Modifier.weight(1f))
            IconButton(onClick = { showDatePicker() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter_alt),
                    contentDescription = null
                )
            }
        }

    }

    @Composable
    private fun GamesContent(games: List<GameData>) {
        LazyColumn(content = {
            itemsIndexed(games) { index, item ->
                Column {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "${parseDateToString(item.date)} - ${item.homeTeam.name}  ${item.homeTeamScore} : ${item.visitorTeamScore} ${item.visitorTeam.name}")
                    }
                    if (index < games.lastIndex)
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                }
            }
        })
    }

    private fun showDatePicker() {
        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    Pair(viewModel.startDate.time, viewModel.endDate.time)
                )
                .build()
        dateRangePicker.addOnPositiveButtonClickListener {
            viewModel.onCalendarPick(it)
        }
        dateRangePicker.show(supportFragmentManager, "")
    }


}