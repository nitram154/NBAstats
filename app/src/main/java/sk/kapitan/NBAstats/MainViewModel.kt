package sk.kapitan.NBAstats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.core.KoinApplication.Companion.init
import sk.kapitan.NBAstats.data.NBARepository
import sk.kapitan.NBAstats.data.models.TeamData

class MainViewModel(private val nbaRepository: NBARepository):ViewModel()  {
    val teams = MutableLiveData<List<TeamData>>()
    init {
        viewModelScope.launch {
           teams.value =  nbaRepository.getTeams()
        }
    }
}