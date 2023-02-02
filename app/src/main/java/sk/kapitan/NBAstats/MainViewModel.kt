package sk.kapitan.NBAstats

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sk.kapitan.NBAstats.data.NBARepository

class MainViewModel(private val nbaRepository: NBARepository) : ViewModel() {


    val teams = MutableLiveData<List<TeamItem>>()

    init {
        viewModelScope.launch {
            val favoriteId = nbaRepository.getFavoriteTeam()
            teams.value = nbaRepository.getTeams().map {
                TeamItem(
                    isFavorite = it.id == favoriteId,
                    data = it
                )
            }
        }
    }

    fun setFavorite(item: TeamItem) {
        nbaRepository.setFavorite(item.data.id)
        teams.value = teams.value?.map {
            it.copy(isFavorite = it.data.id == item.data.id)
        }
    }
}