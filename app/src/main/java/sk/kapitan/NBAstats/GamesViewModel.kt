package sk.kapitan.NBAstats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init
import sk.kapitan.NBAstats.data.NBARepository
import sk.kapitan.NBAstats.data.models.GameData
import sk.kapitan.NBAstats.data.models.TeamData
import java.time.Instant
import java.util.Date
import androidx.core.util.Pair

class GamesViewModel(private val teamId: Int, private val nbaRepository: NBARepository):ViewModel()  {


    val games = MutableLiveData<List<GameData>>()
    val team = MutableLiveData<TeamData>()

    val endDate = Date()
    val startDate = Date(endDate.time - 2592000000)

    init {
        viewModelScope.launch {
            team.value = nbaRepository.getTeamById(teamId)
            games.value =  nbaRepository.getGames(teamId, startDate, endDate)

        }
    }

    fun onCalendarPick(it: Pair<Long, Long>?) {
        it ?: return
        startDate.time = it.first
        endDate.time = it.second
        viewModelScope.launch {
            games.value =  nbaRepository.getGames(teamId, startDate, endDate)
        }
    }
}