package sk.kapitan.NBAstats

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.androidx.viewmodel.ext.android.viewModel
import sk.kapitan.NBAstats.ui.theme.NBAstatsTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import sk.kapitan.NBAstats.data.models.TeamData

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
                    LazyColumn(content = {
                        itemsIndexed(teams.value?: emptyList()){  index, item ->
                          Column() {
                              Row(modifier = Modifier
                                  .padding(16.dp)
                                  .fillMaxWidth().clickable { goToTeam(item) }){

                                  Text(text = "${item.fullName} - ${item.city} - ${item.division} - ${item.conference}")

                              }
                              if (index < teams.value?.lastIndex ?: 0)
                                  Divider(color = Color.Black, thickness = 1.dp)
                          }
                        }
                    })
                }
            }
        }
    }

    private fun goToTeam(it: TeamData) {
        val intent = Intent(this,GamesActivity::class.java)
        intent.putExtra(GAMES_KEY, it.id)
        startActivity(intent)
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NBAstatsTheme {
    }
}