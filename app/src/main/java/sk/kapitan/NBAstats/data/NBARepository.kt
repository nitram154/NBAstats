package sk.kapitan.NBAstats.data

import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sk.kapitan.NBAstats.TeamItem
import sk.kapitan.NBAstats.data.models.GameData
import sk.kapitan.NBAstats.data.models.TeamData
import sk.kapitan.NBAstats.dateToString
import sk.kapitan.NBAstats.parseDate
import java.util.*

const val START_DATE = "START_DATE"
const val END_DATE = "END_DATE"
const val FAVORITE_TEAM = "FAVORITE_TEAM"

class NBARepository(context: Context, private val nbaService: NBAService) {

    private val endDate: Date = Date()
    private val startDate: Date = Date(endDate.time - 2592000000)

    private var favoriteTeamId: Int? = null

    private val sharedPreferences =
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    private val teams = mutableListOf<TeamData>()

    init {
        startDate.time = sharedPreferences.getLong(START_DATE, startDate.time)
        endDate.time = sharedPreferences.getLong(END_DATE, endDate.time)
        favoriteTeamId = sharedPreferences.getInt(FAVORITE_TEAM, 0).takeIf { it > 0 }
    }

    fun getStartDate() = startDate
    fun getEndDate() = endDate
    fun getFavoriteTeam() = favoriteTeamId

    suspend fun getTeams(): List<TeamData> {
        return withContext(Dispatchers.IO) {
            nbaService.getTeams().body()?.data?.also {
                teams.clear()
                teams.addAll(it)
            } ?: emptyList()
        }
    }

    suspend fun getGames(teamId: Int): List<GameData> {
        val startString = dateToString(startDate) ?: return emptyList()
        val endString = dateToString(endDate) ?: return emptyList()

        return withContext(Dispatchers.IO) {
            nbaService.getGames(teamId, startString, endString).body()?.data ?: emptyList()
        }.sortedBy { parseDate(it.date)?.time }
    }

    fun getTeamById(teamId: Int): TeamData {
        return teams.find { it.id == teamId }!!
    }

    fun setStartEndDates(start: Long, end: Long) {
        startDate.time = start
        endDate.time = end
        sharedPreferences.edit {
            putLong(START_DATE, startDate.time)
            putLong(END_DATE, endDate.time)
        }
    }

    fun setFavorite(teamId: Int) {
        favoriteTeamId = teamId
        sharedPreferences.edit {
            putInt(FAVORITE_TEAM, teamId)
        }
    }
}