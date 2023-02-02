package sk.kapitan.NBAstats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init
import sk.kapitan.NBAstats.data.NBARepository
import sk.kapitan.NBAstats.data.models.GameData
import sk.kapitan.NBAstats.data.models.TeamData

class GamesViewModel(private val teamId: Int, private val nbaRepository: NBARepository):ViewModel()  {
    val games = MutableLiveData<List<GameData>>()
    val team = MutableLiveData<TeamData>()
    init {
        viewModelScope.launch {
            team.value = nbaRepository.getTeamById(teamId)
            games.value =  nbaRepository.getGames(teamId)

        }
    }
}