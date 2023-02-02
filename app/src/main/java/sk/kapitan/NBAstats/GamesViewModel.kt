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

class GamesViewModel(private val teamId: Int, private val nbaRepository: NBARepository) :
    ViewModel() {

    val games = MutableLiveData<List<GameData>>()
    val team = MutableLiveData<TeamData>()

    val startDate
        get() = nbaRepository.getStartDate()

    val endDate
        get() = nbaRepository.getEndDate()

    init {
        viewModelScope.launch {
            team.value = nbaRepository.getTeamById(teamId)
            games.value = nbaRepository.getGames(teamId)
        }
    }

    fun onCalendarPick(it: Pair<Long, Long>?) {
        it ?: return
        nbaRepository.setStartEndDates(it.first, it.second)
        viewModelScope.launch {
            games.value = nbaRepository.getGames(teamId)
        }
    }
}