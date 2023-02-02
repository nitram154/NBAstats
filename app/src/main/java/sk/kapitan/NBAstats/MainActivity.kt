package sk.kapitan.NBAstats

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.koin.androidx.viewmodel.ext.android.viewModel
import sk.kapitan.NBAstats.data.models.TeamData
import sk.kapitan.NBAstats.ui.theme.NBAstatsTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NBAstatsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val teams = viewModel.teams.observeAsState()
                    teams.value?.let { TeamsContent(it) } ?: Box {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    }

    private fun goToTeam(it: TeamData) {
        val intent = Intent(this, GamesActivity::class.java)
        intent.putExtra(GAMES_KEY, it.id)
        startActivity(intent)
    }

    @Composable
    private fun TeamsContent(teams: List<TeamItem>) {
        LazyColumn(content = {
            itemsIndexed(teams) { index, item ->
                Column {
                    Row(
                        modifier = Modifier
                            .clickable { goToTeam(item.data) }
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "${item.data.fullName} - ${item.data.division}")
                        Box(Modifier.weight(1f))
                        IconButton(
                            onClick = { viewModel.setFavorite(item) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (item.isFavorite) {
                                        R.drawable.ic_star
                                    } else {
                                        R.drawable.ic_star_border
                                    }
                                ), contentDescription = null
                            )
                        }
                    }
                    if (index < teams.lastIndex)
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                }
            }
        })
    }
}
